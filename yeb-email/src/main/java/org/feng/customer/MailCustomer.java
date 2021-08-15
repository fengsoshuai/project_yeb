package org.feng.customer;

import org.feng.server.entity.Employee;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 邮件消息消费者
 */
@Component
public class MailCustomer {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "mail.welcome")
    public void handler(Employee employee){
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        try {
            // 发件人
            helper.setFrom(mailProperties.getUsername());
            // 收件人
            helper.setTo(employee.getEmail());
            helper.setSubject("入职通知");
            helper.setSentDate(new Date());

            // 邮件内容
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJoblevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            String mail = templateEngine.process("email", context);
            helper.setText(mail, true);
            javaMailSender.send(msg);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

}
