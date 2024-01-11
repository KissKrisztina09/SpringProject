package com.example.SpringProject.model;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private int status;
    private T data;

    public ApiResponse(boolean success, String message,int status, T data) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}