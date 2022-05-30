package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.service.AppUserLoginDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;



@Service
public class AppUserDetailsService {

    private final AppUserDetailsRepo appUserDetailsRepo;

    @Autowired
    public AppUserDetailsService(AppUserDetailsRepo appUserDetailsRepo) {
        this.appUserDetailsRepo = appUserDetailsRepo;
    }

    public AppUserDetails getUserDetails(String id, String username) {
        if(appUserDetailsRepo.existsById(id)) {
            return appUserDetailsRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            "Details of app user with " + id + " not found."));
        }
        return appUserDetailsRepo.insert(AppUserDetails.builder()
                    .id(id)
                    .username(username)
                    .build());
    }

    public List<OrganicBox> getSubscribedBoxes(String id) throws NoSuchElementException {
        return appUserDetailsRepo
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found in UserDetailsRepo with id: " + id))
                .getSubscribedBoxes();
    }
}