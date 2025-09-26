package com.home.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link MailService} interface.
 * 
 * <p>This service handles the actual sending of email notifications
 * using Spring's MailSender for email delivery.
 */
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailSender mailSender;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMailEverydayMail() {
        // Implementation for sending daily email notifications
    }

    /**
     * Sends an email message to the specified recipient.
     * 
     * @param to the recipient email address
     * @param subject the email subject
     * @param text the email content
     */
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
