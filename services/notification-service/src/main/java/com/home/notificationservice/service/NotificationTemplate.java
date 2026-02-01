package com.home.notificationservice.service;

public record NotificationTemplate(
        String subject,
        String content
) {
    public static NotificationTemplate greeting(String email) {
        return new NotificationTemplate(
                "Welcome to Super Pet!",
                "Hello " + email + "! Welcome to our service."
        );
    }

    public static NotificationTemplate userUpdate(String email) {
        return new NotificationTemplate(
                "Profile Updated",
                "Hello " + email + "! Your profile has been updated."
        );
    }

    public static NotificationTemplate userDeletion(String email) {
        return new NotificationTemplate(
                "Account Deleted",
                "Hello " + email + "! Your account has been deleted. We're sorry to see you go."
        );
    }

    public static NotificationTemplate dailyNotification(String email, int taskCount) {
        return new NotificationTemplate(
                "Daily Task Summary",
                "Hello " + email + "! You have " + taskCount + " tasks pending."
        );
    }
}

