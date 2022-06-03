package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/allBoxes")
public class PublicListsController {

    private final BoxDetailsService boxDetailsService;

    public PublicListsController(BoxDetailsService boxDetailsService) {
        this.boxDetailsService = boxDetailsService;
    }

    @GetMapping
    public List<String> getNamesOfAllBoxes(){
        // Todo: Continue implementation, when bug #50 is fixed.
        return List.of("Fruits", "Regional");
    }
}
