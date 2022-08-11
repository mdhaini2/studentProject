package com.montytraining.student.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }}
