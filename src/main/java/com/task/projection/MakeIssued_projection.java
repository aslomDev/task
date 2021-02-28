package com.task.projection;

import java.util.Date;

public interface MakeIssued_projection {

    Long getInvoice_id();

    Date getInvoice_date();

    Long getOrder_id();

    Date getOrder_date();

}
