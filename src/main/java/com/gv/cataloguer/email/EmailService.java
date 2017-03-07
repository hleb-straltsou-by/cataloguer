package com.gv.cataloguer.email;

import java.io.File;

public interface EmailService {

    void sendMessage(String fromEmail, String toEmail, String subject, String messageText);

    void sendMessageWithAttachment(String fromEmail, String toEmail, String subject,
                                   String messageText, File resource);
}
