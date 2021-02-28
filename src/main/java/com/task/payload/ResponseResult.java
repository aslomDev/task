package com.task.payload;

import lombok.Data;

@Data
public class ResponseResult {

    private String message;

    private Object object;

    private Integer invoice_number;

    private Double amount;

    private boolean result;


    public ResponseResult(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    public ResponseResult(String message, Integer invoice_number, boolean result) {
        this.message = message;
        this.invoice_number = invoice_number;
        this.result = result;
    }

    public ResponseResult(String message, Object object, boolean result) {
        this.message = message;
        this.object = object;
        this.result = result;
    }
}
