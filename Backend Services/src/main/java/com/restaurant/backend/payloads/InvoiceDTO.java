package com.restaurant.backend.payloads;

import com.restaurant.backend.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceDTO extends BaseDTO {
    private long id;
    private long order_id;
    private long tableCode;
    private double total;
    private String paymentMethod;
    private String tax;
    private List<OrderInvoiceDTO> itemOrders;
    private List<OrderInvoiceDTO> dealOrders;
}
