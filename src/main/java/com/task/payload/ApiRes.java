package com.task.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiRes {
    private String status;
    private Integer invoice_number;
}
