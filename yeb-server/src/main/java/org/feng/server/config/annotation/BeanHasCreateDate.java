package org.feng.server.config.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解：实体类有 createDate 属性
 * @author FengJinSong
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface BeanHasCreateDate {
}
