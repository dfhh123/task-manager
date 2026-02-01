package com.home.service

import com.home.avro.event.user.UserCreatedEvent
import com.home.avro.event.user.UserDeletedEvent
import com.home.avro.event.user.UserUpdatedEvent
import com.home.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class LinkedUserServiceImpl(
    private val taskRepository: TaskRepository
) : LinkedUserService {
    override fun deleteUser(userEvent: UserDeletedEvent) {
        taskRepository.deleteTaskByLinkedUserId(userEvent.id)
    }
}