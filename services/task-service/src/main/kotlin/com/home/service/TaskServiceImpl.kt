package com.home.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.home.entity.Task
import com.home.entity.TaskDto
import com.home.entity.TaskMapper
import com.home.repository.TaskRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.util.Optional
import java.util.UUID

@Service
class TaskServiceImpl(
    private val taskMapper: TaskMapper,
    private val taskRepository: TaskRepository,
    private val objectMapper: ObjectMapper
) : TaskService {

    override fun getOne(id: UUID): TaskDto {
        val taskOptional: Optional<Task> = taskRepository.findById(id)
        return taskMapper.toTaskDto(taskOptional.orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `$id` not found")
        })
    }

    override fun create(dto: TaskDto): TaskDto {
        val task: Task = taskMapper.toEntity(dto)
        val resultTask: Task = taskRepository.save(task)
        return taskMapper.toTaskDto(resultTask)
    }

    @Throws(IOException::class)
    override fun patch(id: UUID, patchNode: JsonNode): TaskDto {
        val task: Task = taskRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `$id` not found")
        }
        val taskDto = taskMapper.toTaskDto(task)
        objectMapper.readerForUpdating(taskDto).readValue<TaskDto>(patchNode)
        taskMapper.updateWithNull(taskDto, task)
        val resultTask: Task = taskRepository.save(task)
        return taskMapper.toTaskDto(resultTask)
    }

    override fun update(id: UUID, dto: TaskDto): TaskDto {
        val task: Task = taskRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `$id` not found")
        }
        taskMapper.updateWithNull(dto, task)
        val resultTask: Task = taskRepository.save(task)
        return taskMapper.toTaskDto(resultTask)
    }

    override fun delete(id: UUID): TaskDto? {
        val task: Task? = taskRepository.findById(id).orElse(null)
        if (task != null) {
            taskRepository.delete(task)
        }
        return task?.let(taskMapper::toTaskDto)
    }

}