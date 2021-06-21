package org.feng.util;

import cn.hutool.core.util.StrUtil;

import java.util.Scanner;

/**
 * String 工具类
 * @author Administrator
 * @see cn.hutool.core.util.StrUtil
 */
public class StringUtil extends StrUtil {

    /**
     * 控制台输入
     * @param tip 提示语
     * @return 输入的内容，使用 <code>scanner.next()</code>接收
     */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        System.out.println(tip);

        if(scanner.hasNext()){
            String input = scanner.next();
            if(StringUtil.isNotEmpty(input)){
                return input;
            }
        }
        throw  new IllegalArgumentException("请输入正确的提示：" + tip);
    }
}
