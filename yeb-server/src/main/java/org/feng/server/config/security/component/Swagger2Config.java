package org.feng.server.config.security.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger 配置 & Cors 配置
 * @author FengJinSong
 */
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {

    /**
     * 规定扫描哪些包
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.feng.server.controller"))
                .paths(PathSelectors.any())
                .build()
                //.securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }



    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("接口文档")
                .contact(new Contact("FengJinSong", "http://localhost/doc.html", "fengsoshuai@163.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes(){
        // 设置请求头信息
        return Collections.singletonList(new ApiKey("Authorization", "Authorization", "Header"));
    }

    private List<SecurityContext> securityContexts(){
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(getContextByPath("/hello/.*"));
        return securityContexts;
    }
    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
