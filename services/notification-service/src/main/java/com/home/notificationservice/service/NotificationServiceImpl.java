package com.home.notificationservice.service;

import com.home.notificationservice.dto.NotificationData;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendGreetingNotification(UUID id) {
        eventPublisher.publishEvent(new NotificationEvent(userService.getUserEmail(id),
                NotificationEvent.EventType.GREETING));
    }

    @Override
    public void sendUserUpdateNotification(UUID id) {
        eventPublisher.publishEvent(new NotificationEvent(userService.getUserEmail(id),
                NotificationEvent.EventType.USER_UPDATE));
    }

    @Override
    public void sendUserDeletionNotification(UUID id) {
        eventPublisher.publishEvent(new NotificationEvent(userService.getUserEmail(id),
                NotificationEvent.EventType.USER_DELETE));
    }
}
