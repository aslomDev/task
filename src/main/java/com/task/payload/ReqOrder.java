package com.task.payload;

import lombok.Data;

@Data
public class ReqOrder {

    private Integer customerId;

    private Integer productId;

    private Short quantity;


}
