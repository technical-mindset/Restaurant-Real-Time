package com.restaurant.backend.form;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private String username;
}
