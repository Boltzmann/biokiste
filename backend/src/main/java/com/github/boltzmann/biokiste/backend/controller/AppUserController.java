package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.security.service.AppUserLoginDetailsService;
import com.github.boltzmann.biokiste.backend.service.AppUserDetailsService;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user/")
public class AppUserController {

    private final BoxDetailsService boxDetailsService;
    private final AppUserLoginDetailsService appUserLoginDetailsService;
    private final AppUserDetailsService appUserDetailsService;

    @Autowired
    public AppUserController(
            BoxDetailsService boxDetailsService,
            AppUserLoginDetailsService appUserLoginDetailsService,
            AppUserDetailsService appUserDetailsService
    ) {
        this.boxDetailsService = boxDetailsService;
        this.appUserLoginDetailsService = appUserLoginDetailsService;
        this.appUserDetailsService = appUserDetailsService;
    }

    @GetMapping("/me")
    public AppUserDetails getLoggedInUserDetails(Principal principal){
        String id = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        return appUserDetailsService.getUserDetails(id, principal.getName());
    }

    @GetMapping("/subscribedBoxes")
    public List<OrganicBox> getSubscribedBoxes(Principal principal){
        String id = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        return boxDetailsService.getBoxesByUser(id);
    }


}
