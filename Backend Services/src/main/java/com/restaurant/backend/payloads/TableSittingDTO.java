package com.restaurant.backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TableSittingDTO extends BaseDTO {
    private long id;
    private boolean isReserved;
}
