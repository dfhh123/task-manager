package com.home.controller

import com.fasterxml.jackson.databind.JsonNode
import com.home.entity.TaskDto
import com.home.service.TaskService
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.util.*

@RestController("/tasks")
class TaskCrudController(private val taskService: TaskService) {
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID): TaskDto = taskService.getOne(id)

    @PostMapping
    fun create(@RequestBody dto: TaskDto): TaskDto = taskService.create(dto)

    @PatchMapping("/{id}")
    @Throws(IOException::class)
    fun patch(@PathVariable id: UUID, @RequestBody patchNode: JsonNode): TaskDto = taskService.patch(id, patchNode)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody dto: TaskDto): TaskDto = taskService.update(id, dto)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): TaskDto? = taskService.delete(id)
}