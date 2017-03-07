package com.gv.cataloguer.email.concurrency;

import com.gv.cataloguer.email.EmailServiceGMail;
import java.util.List;

/**
 * Execution stream that performs sending email basic messages to specified
 * list of addresses
 */
public class EmailSender extends Thread {

    /** property - who sends email message */
    private String from;

    /** property - who gets email message */
    private List<String> destinationList;

    /** property - subject of email message */
    private String subject;

    /** property - message content */
    private String messageText;

    /**
     * constructor of thread object that specifies parameters of email sending
     * @param from - who sends email message
     * @param destinationList - who gets email message
     * @param subject - subject of email message
     * @param messageText - message content
     */
    public EmailSender(String from, List<String> destinationList, String subject, String messageText) {
        this.from = from;
        this.destinationList = destinationList;
        this.subject = subject;
        this.messageText = messageText;
    }

    @Override
    /**
     * executes email sending with specified parameters
     */
    public void run() {
        for(String to : destinationList) {
            EmailServiceGMail.getInstance().sendMessage(from, to, subject, messageText);
        }
    }
}
