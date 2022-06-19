package com.github.boltzmann.biokiste.backend.security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class AppUserDto {
    private String username;
    private String password;
    private String email;
    private String verified;
    private String verificationCode;
}
