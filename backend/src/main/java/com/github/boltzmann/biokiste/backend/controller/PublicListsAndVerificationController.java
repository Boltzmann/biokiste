package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PublicListsAndVerificationController {
    private final BoxDetailsService boxDetailsService;

    public PublicListsAndVerificationController(
            BoxDetailsService boxDetailsService) {
        this.boxDetailsService = boxDetailsService;
    }
    @GetMapping("/allBoxes")
    public List<OrganicBoxDto> getNamesAndIdsOfAllBoxes(){
        return boxDetailsService.getAllBoxNamesAndId();
    }

}

