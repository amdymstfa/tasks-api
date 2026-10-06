package com.moustapha.tasks_api.entity;

import com.moustapha.tasks_api.exception.InvalidRequestException;

public enum TaskStatus {

    TODO, IN_PROGRESS, DONE;

    /**
     * Handle the transition status of a task
     *
     * @param next the next status of a task
     * @return true if transition is valid, otherwise false
     */

    public boolean canTransitionTo(TaskStatus next){
        return switch (this){
            case TODO -> next == IN_PROGRESS;
            case IN_PROGRESS -> next == DONE;
            case DONE -> false;
        };
    }

    /**
     * Convert raw text into status
     * @throws InvalidRequestException if the value is missing, unknows or empty
     */
    public static TaskStatus parse(String raw){
        if (raw == null || raw.isBlank()){
            throw new InvalidRequestException("You must enter the status of task");
        }

        try {
            return TaskStatus.valueOf(raw.trim());
        }catch(IllegalArgumentException e){
            throw new InvalidRequestException("Allowed transition : TODO, IN_PROGRESS, DONE");
        }
    }
}