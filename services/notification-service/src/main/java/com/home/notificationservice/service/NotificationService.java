package com.home.notificationservice.service;

import java.util.UUID;

public interface NotificationService {

    void sendGreetingNotification(UUID id);

    void sendUserUpdateNotification(UUID id);

    void sendUserDeletionNotification(UUID id);
}
