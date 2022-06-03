package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/allBoxes")
public class PublicListsController {
    private final BoxDetailsService boxDetailsService;

    public PublicListsController(BoxDetailsService boxDetailsService) {
        this.boxDetailsService = boxDetailsService;
    }
    @GetMapping
    public List<OrganicBoxDto> getNamesOfAllBoxes(){
        return boxDetailsService.getAllBoxNamesAndId();
    }
}

