package com.home.notificationservice.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendGreetingsNotification(String id) {
        eventPublisher.publishEvent(new NotificationEvent(userService.getUserEmail(id),
                NotificationEvent.EventType.GREETING));
    }

    @Override
    public void sendUserUpdateNotification(String id) {
        eventPublisher.publishEvent(new NotificationEvent(userService.getUserEmail(id),
                NotificationEvent.EventType.USER_UPDATE));
    }

    @Override
    public void sendUserDeletionNotification(String id) {
        eventPublisher.publishEvent(new NotificationEvent(userService.getUserEmail(id),
                NotificationEvent.EventType.USER_DELETE));
    }
}
