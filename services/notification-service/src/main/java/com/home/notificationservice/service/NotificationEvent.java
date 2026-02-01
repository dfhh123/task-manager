package com.home.notificationservice.service;

public record NotificationEvent(
        String email,
        EventType eventType
) {
    public enum EventType {
        GREETING,
        USER_UPDATE,
        USER_DELETE,
        DAILY_NOTIFICATION
    }
}
