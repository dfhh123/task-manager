package com.home.controller

import com.fasterxml.jackson.databind.JsonNode
import com.home.entity.TaskCreateDto
import com.home.entity.TaskDto
import jakarta.validation.Valid
import com.home.service.TaskService
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.util.*

@RestController()
@RequestMapping("/api/rest/v1/tasks")
class TaskCrudController(private val taskService: TaskService) {
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID): TaskDto = taskService.getOne(id)

    @GetMapping("/by-user")
    fun getManyByLinkedUserId(@RequestParam linkedUserId: UUID): List<TaskDto> {
        return taskService.findManyByLinkedUserId(linkedUserId)
    }

    @PostMapping
    fun create(@RequestBody @Valid dto: TaskCreateDto): TaskDto = taskService.create(dto)

    @PatchMapping("/{id}")
    @Throws(IOException::class)
    fun patch(@PathVariable id: UUID, @RequestBody patchNode: JsonNode): TaskDto = taskService.patch(id, patchNode)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody dto: TaskDto): TaskDto = taskService.update(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): TaskDto? = taskService.delete(id)
}