package com.gv.cataloguer.email;

import org.junit.Test;

import java.io.File;

public class EmailServiceGMailTest {

    @Test
    public void sendMessage() throws Exception {
        String from = "ka1oken4by@gmail.com";
        String to = "gleb.streltsov.4by@gmail.com";
        String subject = "test";
        String messageText = "test message";
        EmailServiceGMail.getInstance().sendMessage(from, to, subject, messageText);
    }

    @Test
    public void sendMessageWithAttachment() throws Exception {
        String from = "ka1oken4by@gmail.com";
        String to = "gleb.streltsov.4by@gmail.com";
        String subject = "test";
        String messageText = "test message";
        String resourcePath = "G:\\Archive\\desktop\\Cataloguer\\local catalog\\documents\\Streltsov_Gleb_characteristic_2_course.doc";
        File file = new File(resourcePath);
        EmailServiceGMail.getInstance().sendMessageWithAttachment(from, to, subject, messageText, file);
    }
}