package com.home.evets

import com.home.avro.event.user.UserDeletedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaEventListener(
    private val eventPublisher: ApplicationEventPublisher
){
    @KafkaListener(topics = ["user-deleted-event"], groupId = "task-service")
    fun handleUserDeleted(event: UserDeletedEvent) {
        eventPublisher.publishEvent(event)
    }
}