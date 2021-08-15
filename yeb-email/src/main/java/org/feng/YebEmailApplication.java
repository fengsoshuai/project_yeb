package org.feng;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 *
 * @author Fengjs
 */
@SpringBootApplication
public class YebEmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebEmailApplication.class, args);
    }

    @Bean
    public Queue queue(){
        return new Queue("mail.welcome");
    }
}
