package com.restaurant.backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/** @Asad_Zaidi */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemDTO extends BaseDTO{
    private long id;
    private String name;
    private String description;
    private double price;

    // takes only item category id;
    private Integer itemCategory;
}
