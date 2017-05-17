package com.gv.cataloguer.email;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class EmailServiceGMailTest {

    private ApplicationContext context = new ClassPathXmlApplicationContext("IoC/email-context.xml");

    private static final String EMAIL_SERVICE_GMAIL_BEAN = "emailServiceGmail";

    @Test
    public void sendMessage() throws Exception {
        String from = "ka1oken4by@gmail.com";
        String to = "gleb.streltsov.4by@gmail.com";
        String subject = "test";
        String messageText = "test message";
        EmailService service = (EmailService)context.getBean(EMAIL_SERVICE_GMAIL_BEAN);
        service.sendMessage(from, to, subject, messageText);
    }

    @Test
    public void sendMessageWithAttachment() throws Exception {
        String from = "ka1oken4by@gmail.com";
        String to = "gleb.streltsov.4by@gmail.com";
        String subject = "test";
        String messageText = "test message";
        String resourcePath = "G:\\Archive\\desktop\\Cataloguer\\local catalog\\documents\\Streltsov_Gleb_characteristic_2_course.doc";
        File file = new File(resourcePath);
        EmailService service = (EmailService)context.getBean(EMAIL_SERVICE_GMAIL_BEAN);
        service.sendMessageWithAttachment(from, to, subject, messageText, file);
    }
}