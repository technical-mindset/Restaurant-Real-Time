package com.restaurant.backend.payloads;

import com.restaurant.backend.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceDTO {
    private long id;
    private String paymentMethod;
    private String tax;
    private long order_id;
    private Restaurant restaurant;
}
