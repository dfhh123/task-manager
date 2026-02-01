package com.home.notificationservice.service;

import com.home.notificationservice.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> getTasksByUserId(String userId);

}
