package com.moustapha.tasks_api.exception;

import com.moustapha.tasks_api.entity.TaskStatus;

public class InvalidTransitionException extends RuntimeException{

    public InvalidTransitionException(TaskStatus from, TaskStatus to){
        super("Impossible to move from : " + from + "to" + to +
                ". Allowed transition : TODO -> IN_PROGRESS -> DONE");
    }
}