package com.restaurant.backend.payloads;

import com.restaurant.backend.model.Roles;
import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO extends BaseDTO {
    private int id;

    @Min(value = 3, message = Constants.MIN_VALUE)
    private String userName;

    @Min(value = 5, message = Constants.MIN_VALUE)
    private String password;
    private long restaurant;

    @Min(value = 1, message = Constants.EMPTY_MESSAGE)
    private List<Roles> role = new ArrayList<>();
}
