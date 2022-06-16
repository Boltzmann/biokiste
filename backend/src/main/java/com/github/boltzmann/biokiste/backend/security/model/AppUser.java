package com.github.boltzmann.biokiste.backend.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("appusers")
@Builder
public class AppUser {
    @Id
    private String id;
    private String username;
    private String email;
    private boolean verified;
    private String verificationCode;
    private String password;
}
