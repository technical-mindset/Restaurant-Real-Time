package com.restaurant.backend.payloads;

import com.restaurant.backend.model.Order;
import com.restaurant.backend.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TableSittingAdminDTO extends BaseDTO{
    private int tableCode;
    private boolean isReserved;
    private Restaurant restaurant;
}
