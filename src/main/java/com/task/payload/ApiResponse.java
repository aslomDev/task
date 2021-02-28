package com.task.payload;

import lombok.Data;

@Data
public class ApiResponse {

    private String message;

    private Object object;

    private boolean result;

    public ApiResponse(String message, Object object, boolean result) {
        this.message = message;
        this.object = object;
        this.result = result;
    }

    public ApiResponse(String message, boolean result) {
        this.message = message;
        this.result = result;
    }
}
