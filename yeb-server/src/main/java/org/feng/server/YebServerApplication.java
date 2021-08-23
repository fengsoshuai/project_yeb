package org.feng.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/**
 * 启动类
 *  java -jar project.jar --spring.config.location=/usr/src/application.yml
 * @author Fengjs
 */
@SpringBootApplication
@MapperScan("org.feng.server.mapper")
public class YebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebServerApplication.class, args);
    }
}
