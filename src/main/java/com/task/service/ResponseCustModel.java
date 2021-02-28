package com.task.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCustModel {
    private Integer customer_id;

    private String name;

    private Date lastOrderDate;
}
