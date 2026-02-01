package com.home.service

import com.home.avro.event.user.UserDeletedEvent

interface LinkedUserService {
    fun deleteUser(userCreatedEvent: UserDeletedEvent)
}
