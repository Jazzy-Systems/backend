package com.jazzysystems.backend.auth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponsePOJO {

    private String accessToken;

    private String type = "Bearer";

    private String email;

    private String role;

    public JwtResponsePOJO(String accessToken, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.email = email;
        this.role = roles.get(0);
    }
}
