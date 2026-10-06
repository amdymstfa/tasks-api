package com.moustapha.tasks_api.exception;

public class InvalidRequestException extends RuntimeException{

    public InvalidRequestException(String message){
        super(message);
    }
}