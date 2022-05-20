package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.AppUserDetails;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserRepository;
import com.github.boltzmann.biokiste.backend.security.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user/")
public class AppUserController {

    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public AppUserController(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }


    @GetMapping("/me")
    public AppUserDetails getLoggedInUserDetails(Principal principal){
        AppUser appUser =this.appUserDetailsService
                .getUserByName(principal.getName());
        return AppUserDetails.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .customerId(appUser.getCustomerId())
                .build();
    }
}
