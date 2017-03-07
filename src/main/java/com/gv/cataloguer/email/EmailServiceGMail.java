package com.gv.cataloguer.email;

import com.gv.cataloguer.logging.AppLogger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * implementation of EmailService interface using GMail via TLS and singleton pattern
 */
public class EmailServiceGMail implements EmailService {

    /** single instance of class */
    private static EmailServiceGMail INSTANCE = new EmailServiceGMail();

    /** java mail session object */
    private static Session SESSION;

    /** object for retrieving mail settings */
    private static Properties PROPERTIES;

    /**
     * Returns single instance of class
     * @return EmailServiceGMail instance
     */
    public static EmailServiceGMail getInstance(){
        return INSTANCE;
    }

    /**
     * initializes session and properties objects for getting ready sending emails
     */
    private EmailServiceGMail(){
        InputStream input = getClass().getClassLoader().getResourceAsStream("mail.properties");
        try {
            PROPERTIES = new Properties();
            PROPERTIES.load(input);
        } catch (IOException e){
            e.printStackTrace();
        }
        SESSION = Session.getInstance(PROPERTIES,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(PROPERTIES.getProperty("mail.username"),
                                PROPERTIES.getProperty("mail.password"));
                    }
                });
    }

    @Override
    public void sendMessage(String fromEmail, String toEmail, String subject, String messageText) {
        try {
            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessageWithAttachment(String fromEmail, String toEmail, String subject,
                                          String messageText, File resource) {
        try {
            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(messageText);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(resource);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(resource.getName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            AppLogger.getLogger().error(e.getMessage());
        }
    }
}
