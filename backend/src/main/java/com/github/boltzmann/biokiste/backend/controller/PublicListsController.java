package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping("/allBoxes")
public class PublicListsController {

    @GetMapping
    public List<OrganicBoxDto> getNamesOfAllBoxes(){
        OrganicBoxDto fruits = OrganicBoxDto.builder().id("1").name("Fruits").build();
        OrganicBoxDto regional = OrganicBoxDto.builder().id("2").name("Regional").build();
        List<OrganicBoxDto> tmp = new ArrayList<>();
        tmp.add(fruits);
        tmp.add(regional);
        return tmp;
    }
}






/*
    private final BoxDetailsService boxDetailsService;

    public PublicListsController(BoxDetailsService boxDetailsService) {
        this.boxDetailsService = boxDetailsService;
    }

 */
