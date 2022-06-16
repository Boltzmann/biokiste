package com.github.boltzmann.biokiste.backend.security.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AppUserDto {
    private String username;
    private String password;
    private String email;
}
