package com.restaurant.backend.payloads;


import com.restaurant.backend.payloads.BaseDTO;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Order "+ Constants.EMPTY_MESSAGE)
    private long order_id;
}
