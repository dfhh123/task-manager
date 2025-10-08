package com.home.entity

import org.mapstruct.Mapper
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class TaskMapper {
    abstract fun toTaskDto(task: Task): TaskDto

    abstract fun toEntity(taskDto: TaskDto): Task
    abstract fun updateWithNull(taskDto: TaskDto, @MappingTarget task: Task): Task
}