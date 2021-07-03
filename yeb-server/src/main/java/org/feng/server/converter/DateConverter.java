package org.feng.server.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换，从业面传回日期时，自动转换
 * @author FengJinSong
 */
@Component
public class DateConverter  implements Converter<String, LocalDate>  {

    /**
     * 日期转换
     * @param source 日期字符串
     * @return LocalDate，当转换出错，取 LocalDate.MIN
     */
    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e){
            e.printStackTrace();
            return LocalDate.MIN;
        }
    }
}
