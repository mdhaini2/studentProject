package com.montytraining.student.Entities;

public class Response {
    private Object data;
    private int status;
    private String message;

    public Response(Object data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }
}
