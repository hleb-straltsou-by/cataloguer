package com.gv.cataloguer.email.concurrency;

import com.gv.cataloguer.email.EmailServiceGMail;

import java.util.List;

public class EmailSender extends Thread {

    private String from;
    private List<String> destinationList;
    private String subject;
    private String messageText;

    public EmailSender(String from, List<String> destinationList, String subject, String messageText) {
        this.from = from;
        this.destinationList = destinationList;
        this.subject = subject;
        this.messageText = messageText;
    }

    @Override
    public void run() {
        for(String to : destinationList) {
            EmailServiceGMail.getInstance().sendMessage(from, to, subject, messageText);
        }
    }
}
