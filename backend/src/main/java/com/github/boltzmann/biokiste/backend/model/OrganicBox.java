package com.github.boltzmann.biokiste.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class OrganicBox {
    @Id
    String id;
    String name;
    int weekOfYear;
    String size;
    List<Item> content;
}
