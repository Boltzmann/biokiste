package com.github.boltzmann.biokiste.backend.security.repository;

import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
}
