package com.home.service;

/**
 * Service interface for managing user notifications.
 * 
 * <p>This interface defines methods for sending various types of user notifications,
 * including greetings, updates, and deletion notifications.
 */
public interface UserNotifications {

    /**
     * Sends a greeting notification to users.
     * This method is typically called when new users register.
     */
    void sendUserGreetingsNotification();

    /**
     * Sends a notification when user information is updated.
     * This method is called when user profile changes occur.
     */
    void sendUserUpdatedNotification();

    /**
     * Sends a notification when a user account is deleted.
     * This method is called during user account removal process.
     */
    void sendUserDeletedNotification();
}
