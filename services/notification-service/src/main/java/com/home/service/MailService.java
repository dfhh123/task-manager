package com.home.service;

/**
 * Service interface for sending email notifications.
 * 
 * <p>This interface defines methods for sending various types of email notifications
 * to users, including daily reports and other automated messages.
 */
public interface MailService {
    /**
     * Sends daily email notifications to users.
     * This method is typically called by a scheduled job.
     */
    void sendMailEverydayMail();
}
