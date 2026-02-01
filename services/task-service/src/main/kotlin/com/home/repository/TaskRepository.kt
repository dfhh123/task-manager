package com.home.repository

import com.home.entity.TaskFilter
import com.home.entity.Task
import com.home.entity.TaskDto
import org.springframework.data.domain.Slice
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface TaskRepository : MongoRepository<Task, UUID> {

    fun findAllByLinkedUserId(linkedUserId: UUID): MutableList<Task>

    fun deleteTaskByLinkedUserId(linkedUserId: UUID)
}