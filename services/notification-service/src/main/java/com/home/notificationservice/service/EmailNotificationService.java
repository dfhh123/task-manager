package com.home.notificationservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@AllArgsConstructor
@Slf4j
public class EmailNotificationService {

//    //TODO(корректно вставить апи кей)
//    @Value("")
//    private final String apiKey;

    @EventListener
    public void handleUserEvent(NotificationEvent eventData) {
        switch (eventData.eventType()) {
            case DAILY_NOTIFICATION -> sendDailyNotification(eventData);

            case GREETING -> sendGreetingNotification(eventData);

            case USER_UPDATE -> sendUserUpdateNotification(eventData);

            case USER_DELETE -> sendUserDeletionNotification(eventData);
        }
    }

    private void sendUserDeletionNotification(NotificationEvent eventData) {
        //TODO(временный мок)
        log.info("уведомляем пользователя об удалении ака");

    }

    private void sendUserUpdateNotification(NotificationEvent eventData) {
        log.info("уведомляем пользователя об обновлении ака");
    }

    private void sendDailyNotification(NotificationEvent eventData) {
        log.info("уведомляем пользователя ежедневным уведомлением");
    }

    public void sendGreetingNotification(NotificationEvent eventData) {
        log.info("уведомляем пользователя об регистрации ака");
    }
}
