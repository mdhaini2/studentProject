package com.montytraining.student.Exception;

public class IdNotValidException extends RuntimeException{
    public IdNotValidException(String error){
        super(error);
    }
}
