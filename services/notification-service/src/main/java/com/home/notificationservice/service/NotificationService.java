package com.home.notificationservice.service;

public interface NotificationService {

    void sendGreetingsNotification(String id);

    void sendUserUpdateNotification(String id);

    void sendUserDeletionNotification(String id);
}
