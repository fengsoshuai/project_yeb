package org.feng.server.config.security.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JWT 工具类
 * @author FengJinSong
 */
@Component
public class JwtTokenUtil {

    /**
     * 用户登陆名
     * UserDetails.username
     */
    private static final String CLAIM_KEY_USERNAME = "sub";
    /**
     * 创建时间：当前时间
     */
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 秘钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * 失效时间
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     * @return token
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取用户名
     * @param token token
     * @return 用户名
     */
    public String getUserNameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = Objects.requireNonNull(claims).getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * token是否可以被刷新：isTokenNoneExpired() 返回true
     * @param token
     * @see
     * @return
     */
    public boolean canRefresh(String token){
        return isTokenNoneExpired(token);
    }

    /**
     * 刷新token，重置创建时间，返回新的token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否有效
     * @param token
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return Objects.equals(username, userDetails.getUsername()) && isTokenNoneExpired(token);
    }

    /**
     * 当前时间超过了失效时间
     * @param token token
     * @return true 未失效
     */
    private boolean isTokenNoneExpired(String token) {
        Date expiredDateFromToken = getExpiredDateFromToken(token);
        return !new Date().after(expiredDateFromToken);
    }

    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 获取荷载
     * @param token token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            e.printStackTrace();
        }

        return claims;
    }

    /**
     * 生成token失效时间
     * @return 从当前时间算起，加上失效时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
