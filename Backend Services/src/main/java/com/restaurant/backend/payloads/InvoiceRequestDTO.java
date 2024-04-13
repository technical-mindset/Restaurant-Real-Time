package com.restaurant.backend.payloads;


import com.restaurant.backend.payloads.BaseDTO;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Payment method "+ Constants.EMPTY_MESSAGE)
    private String paymentMethod;
    private String tax;

    @NotEmpty(message = "Order "+ Constants.EMPTY_MESSAGE)
    private long order_id;
}
