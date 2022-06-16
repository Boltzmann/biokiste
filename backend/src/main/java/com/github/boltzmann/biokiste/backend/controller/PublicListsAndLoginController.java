package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.service.AppUserVerificationDetailsService;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PublicListsAndLoginController {
    private final BoxDetailsService boxDetailsService;

    private final AppUserVerificationDetailsService appUserVerificationDetailsService;

    public PublicListsAndLoginController(BoxDetailsService boxDetailsService, AppUserVerificationDetailsService appUserVerificationDetailsService) {
        this.boxDetailsService = boxDetailsService;
        this.appUserVerificationDetailsService = appUserVerificationDetailsService;
    }
    @GetMapping("/allBoxes")
    public List<OrganicBoxDto> getNamesAndIdsOfAllBoxes(){
        return boxDetailsService.getAllBoxNamesAndId();
    }

    @PostMapping("/verification")
    public void postEmailVerification(@RequestBody AppUserDto appUserDto){
        appUserVerificationDetailsService.sendSimpleMessage();
    }
}

