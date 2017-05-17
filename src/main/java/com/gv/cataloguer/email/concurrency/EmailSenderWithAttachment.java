package com.gv.cataloguer.email.concurrency;

import com.gv.cataloguer.email.EmailService;
import java.io.File;
import java.util.List;

/**
 * Execution stream that performs sending email messages with attachments
 * to specified list of addresses
 */
public class EmailSenderWithAttachment extends Thread{

    private EmailService service;

    /** property - who sends email message */
    private String from;

    /** property - who gets email message */
    private List<String> destinationList;

    /** property - subject of email message */
    private String subject;

    /** property - message content */
    private String messageText;

    /** property - sending resource */
    private File resource;

    public EmailSenderWithAttachment(EmailService service){
        this.service = service;
    }

    /**
     * constructor of thread object that specifies parameters of email sending
     * @param from - who sends email message
     * @param destinationList - who gets email message
     * @param subject - subject of email message
     * @param messageText - message content
     * @param resource - resource attached to the email
     */
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
            service.sendMessageWithAttachment(from, to, subject, messageText, resource);
        }
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setDestinationList(List<String> destinationList) {
        this.destinationList = destinationList;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }
}
