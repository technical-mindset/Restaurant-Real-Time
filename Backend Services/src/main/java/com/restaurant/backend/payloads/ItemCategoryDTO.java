package com.restaurant.backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemCategoryDTO extends BaseDTO{
    private long id;
    private String name;
    private String description;
}
