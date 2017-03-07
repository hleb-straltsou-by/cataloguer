package com.gv.cataloguer.email;

import java.io.File;

/**
 * specifies contract for sending email messages
 */
public interface EmailService {

    /**
     * performs sending basic email message
     * @param fromEmail - who sends email message
     * @param toEmail - who gets email message
     * @param subject - subject of email message
     * @param messageText - message content
     */
    void sendMessage(String fromEmail, String toEmail, String subject, String messageText);

    /**
     * performs sending email message with attachment
     * @param fromEmail - who sends email message
     * @param toEmail - who gets email message
     * @param subject - subject of email message
     * @param messageText - message content
     * @param resource -  - resource attached to the email
     */
    void sendMessageWithAttachment(String fromEmail, String toEmail, String subject,
                                   String messageText, File resource);
}
