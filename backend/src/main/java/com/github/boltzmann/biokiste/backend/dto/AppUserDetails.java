package com.github.boltzmann.biokiste.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//TODO: no dto, rather a model. Refactor.
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class AppUserDetails {
    @Id
    private String id;
    private String username;
    private String customerId;
}
