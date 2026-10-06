package com.moustapha.tasks_api.dto;

import com.moustapha.tasks_api.entity.Task;
import com.moustapha.tasks_api.entity.TaskStatus;

public record TaskResponse(
        Long id, String title, String description, TaskStatus status
) {
    public static TaskResponse from(Task task){
        return new TaskResponse(
                task.getId(), task.getTitle(), task.getDescription(), task.getTaskStatus()
        );
    }
}