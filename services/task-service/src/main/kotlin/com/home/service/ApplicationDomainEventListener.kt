package com.home.service

import com.home.avro.event.user.UserDeletedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class ApplicationDomainEventListener(
    private val userService: LinkedUserService,
) {
    @EventListener
    public fun onUserDeletedEvent(userEvent: UserDeletedEvent) {
        userService.deleteUser(userEvent)
    }
}