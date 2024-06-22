package com.restaurant.backend.payloads;


import com.restaurant.backend.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class UserDTO extends BaseDTO {
    private long id;

    @Size(min = 3, message = Constants.MIN_VALUE)
    private String username;

    @NotBlank(message = "Password " + Constants.EMPTY_MESSAGE)
    @Size(min = 5, message = Constants.MIN_VALUE)
    private String password;
    private long restaurant;

    @NotEmpty(message = "Role " + Constants.EMPTY_MESSAGE)
    private List<Long> role = new ArrayList<>();
}
