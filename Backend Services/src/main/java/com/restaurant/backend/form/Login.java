package com.restaurant.backend.form;


import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Login {
    private String username;
    private String password;
}
