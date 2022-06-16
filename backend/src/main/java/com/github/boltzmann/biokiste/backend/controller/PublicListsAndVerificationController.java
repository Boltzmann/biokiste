package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.service.AppUserDetailsService;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PublicListsAndVerificationController {
    private final BoxDetailsService boxDetailsService;

    private final AppUserDetailsService appUserDetailsService;

    public PublicListsAndVerificationController(
            BoxDetailsService boxDetailsService,
            AppUserDetailsService appUserDetailsService) {
        this.boxDetailsService = boxDetailsService;
        this.appUserDetailsService = appUserDetailsService;
    }
    @GetMapping("/allBoxes")
    public List<OrganicBoxDto> getNamesAndIdsOfAllBoxes(){
        return boxDetailsService.getAllBoxNamesAndId();
    }

    @PostMapping("/verification")
    public AppUserDto postEmailVerification(@RequestBody AppUserDto appUserDto){
        return appUserDetailsService.startUserEmailVerification(appUserDto);
    }
}

