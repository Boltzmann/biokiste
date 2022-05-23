package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;



@Service
public class AppUserDetailsService {

    private final AppUserDetailsRepo appUserDetailsRepo;

    @Autowired
    public AppUserDetailsService(AppUserDetailsRepo appUserDetailsRepo) {
        this.appUserDetailsRepo = appUserDetailsRepo;
    }

    public AppUserDetails getUserDetails(AppUser appUser) {
        if(!appUserDetailsRepo.existsById(appUser.getId())){
            appUserDetailsRepo.insert(AppUserDetails.builder()
                    .id(appUser.getId())
                    .username(appUser.getUsername())
                    .build());
        }
        return appUserDetailsRepo.findById(appUser.getId())
                .orElseThrow( () -> new NoSuchElementException(
                        "Details of app user with " + appUser.getId() + " not found."));
    }

}
