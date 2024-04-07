package com.restaurant.backend.payloads;


import com.restaurant.backend.payloads.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequestDTO extends BaseDTO {
    private long id;
    private String paymentMethod;
    private String tax;
    private long order_id;
}
