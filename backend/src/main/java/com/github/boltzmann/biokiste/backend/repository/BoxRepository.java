package com.github.boltzmann.biokiste.backend.repository;

import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends MongoRepository<OrganicBox, String> {
}
