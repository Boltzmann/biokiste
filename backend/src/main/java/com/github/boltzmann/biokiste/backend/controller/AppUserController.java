package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.security.service.AppUserLoginDetailsService;
import com.github.boltzmann.biokiste.backend.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api/user/")
public class AppUserController {

    private final AppUserLoginDetailsService appUserLoginDetailsService;
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public AppUserController(AppUserLoginDetailsService appUserLoginDetailsService, AppUserDetailsService appUserDetailsService) {
        this.appUserLoginDetailsService = appUserLoginDetailsService;
        this.appUserDetailsService = appUserDetailsService;
    }

    @GetMapping("/me")
    public AppUserDetails getLoggedInUserDetails(Principal principal){
        String id = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        return appUserDetailsService.getUserDetailsById(id);
    }
}
