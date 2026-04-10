package com.staynomadly.api.payload.request;

import com.staynomadly.api.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String fullName;

    @NotNull
    private Role role;
}
