package com.restaurant.backend.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompileOrderDTO {
    private long id;
    private double bill;
    private long tableSitting;
    private List<ItemOrderDTO> itemOrder;
    private List<DealOrderDTO> dealOrder;
}
