package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.service.AppUserLoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;



@Service
public class AppUserDetailsService {

    private final AppUserDetailsRepo appUserDetailsRepo;
    private final AppUserLoginDetailsService appUserLoginDetailsService;

    @Autowired
    public AppUserDetailsService(AppUserDetailsRepo appUserDetailsRepo, AppUserLoginDetailsService appUserLoginDetailsService) {
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.appUserLoginDetailsService = appUserLoginDetailsService;
    }

    public AppUserDetails getUserDetailsById(String id) {
        if(appUserDetailsRepo.existsById(id)) {
            return appUserDetailsRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            "Details of app user with " + id + " not found."));
        }
        return appUserDetailsRepo.insert(AppUserDetails.builder()
                    .id(id)
                    .username(appUserLoginDetailsService.getUsernameById(id))
                    .build());
    }
}
