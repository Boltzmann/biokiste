package com.github.boltzmann.biokiste.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AppUserDetails {
    private String id;
    private String username;
    private String customerId;
}
