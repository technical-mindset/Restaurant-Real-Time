package com.restaurant.backend.payloads;

import com.restaurant.backend.model.Restaurant;
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
public class UserRequestDTO {
    private String userName;
    private String password;
    private Restaurant restaurant;
    private List<Role> role = new ArrayList<>();
}
