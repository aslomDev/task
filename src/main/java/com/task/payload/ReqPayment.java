package com.task.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReqPayment {

    private Integer ivoice_id;

    private BigDecimal summa;

    public ReqPayment(Integer ivoice_id) {
        this.ivoice_id = ivoice_id;
    }

    public ReqPayment(Integer ivoice_id, BigDecimal summa) {
        this.ivoice_id = ivoice_id;
        this.summa = summa;
    }
}
