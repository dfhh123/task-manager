package com.home.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.home.avro.event.task.TaskCreatedEvent
import com.home.avro.event.task.TaskDeletedEvent
import com.home.avro.event.task.TaskUpdatedEvent
import com.home.entity.Task
import com.home.entity.TaskCreateDto
import com.home.entity.TaskDto
import com.home.entity.TaskMapper
import com.home.repository.TaskRepository
import org.springframework.context.ApplicationEventPublisher
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
    private val objectMapper: ObjectMapper,
    private val eventPublisher: ApplicationEventPublisher
) : TaskService {

    override fun getOne(id: UUID): TaskDto {
        val taskOptional: Optional<Task> = taskRepository.findById(id)
        return taskMapper.toTaskDto(taskOptional.orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `$id` not found")
        })
    }

    override fun findManyByLinkedUserId(linkedUserId: UUID): List<TaskDto> {
        return taskRepository.findAllByLinkedUserId(linkedUserId).stream().map { taskMapper.toTaskDto(it) }
            .toList()
    }

    override fun create(dto: TaskCreateDto): TaskDto {
        val task: Task = taskMapper.toEntity(dto)
        val resultTask: Task = taskRepository.save(task)
        eventPublisher.publishEvent(TaskCreatedEvent(resultTask.id))
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
        eventPublisher.publishEvent(TaskUpdatedEvent(resultTask.id))
        return taskMapper.toTaskDto(resultTask)
    }

    override fun update(id: UUID, dto: TaskDto): TaskDto {
        val task: Task = taskRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `$id` not found")
        }
        taskMapper.updateWithNull(dto, task)
        val resultTask: Task = taskRepository.save(task)
        eventPublisher.publishEvent(TaskUpdatedEvent(resultTask.id))
        return taskMapper.toTaskDto(resultTask)
    }

    override fun delete(id: UUID): TaskDto? {
        val task: Task? = taskRepository.findById(id).orElse(null)
        if (task != null) {
            taskRepository.delete(task)
            eventPublisher.publishEvent(TaskDeletedEvent(task.id))
        }
        return task?.let(taskMapper::toTaskDto)
    }

}