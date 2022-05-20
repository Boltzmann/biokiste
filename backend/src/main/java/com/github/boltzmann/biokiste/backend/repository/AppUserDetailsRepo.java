package com.github.boltzmann.biokiste.backend.repository;

import com.github.boltzmann.biokiste.backend.dto.AppUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserDetailsRepo extends MongoRepository<AppUserDetails, String> {
}
