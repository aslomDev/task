package com.task.projection;

import java.util.Date;

public interface Order_without_invoice_projection {

    Long getOrderId();

    Date getOrderDate();

    Double getTotal_price();

}
