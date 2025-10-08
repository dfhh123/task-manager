package com.home.repository;

import com.home.entity.Task
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface TaskRepository : MongoRepository<Task, UUID> {
}