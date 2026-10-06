package com.moustapha.tasks_api.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id){
        super("Task not found : " + id);
    }
}