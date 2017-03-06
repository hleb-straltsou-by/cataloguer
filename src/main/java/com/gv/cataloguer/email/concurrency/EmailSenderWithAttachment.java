package com.gv.cataloguer.email.concurrency;

import com.gv.cataloguer.email.EmailServiceGMail;
import java.io.File;
import java.util.List;

public class EmailSenderWithAttachment extends Thread{

    private String from;
    private List<String> destinationList;
    private String subject;
    private String messageText;
    private File resource;

    public EmailSenderWithAttachment(String from, List<String> destinationList,
                                     String subject, String messageText, File resource) {
        this.from = from;
        this.destinationList = destinationList;
        this.subject = subject;
        this.messageText = messageText;
        this.resource = resource;
    }

    @Override
    public void run() {
        for(String to : destinationList) {
            EmailServiceGMail.getInstance().sendMessageWithAttachment(from, to, subject, messageText, resource);
        }
    }
}
