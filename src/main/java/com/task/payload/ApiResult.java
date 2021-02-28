package com.task.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ApiResult {


    private Integer id;

    private BigDecimal price;

    private String message;

    private Integer invoice_number;


}
