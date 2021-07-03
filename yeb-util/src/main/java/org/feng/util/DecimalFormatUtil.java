package org.feng.util;

import java.text.DecimalFormat;

/**
 * 小数格式化工具类
 *
 * @author FengJinSong
 */
public class DecimalFormatUtil {


    /**
     * 格式化小数，保留2位
     * @param number 目标数值
     * @return 结果
     */
    public static String format(Double number){
        return format(number, "##.00");
    }


    /**
     * 格式化 Double小数为指定格式
     * @param number 数值
     * @param pattern 格式，比如 ##.00 表示保留2为位小数
     * @return 结果
     */
    public static String format(Double number, String pattern){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);
    }
}
