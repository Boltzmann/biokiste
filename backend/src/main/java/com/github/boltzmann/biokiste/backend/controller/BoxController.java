package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/box/")
public class BoxController {

    private final BoxDetailsService boxDetailsService;

    public BoxController(BoxDetailsService boxDetailsService) {
        this.boxDetailsService = boxDetailsService;
    }

    @GetMapping("{id}")
    public List<Item> getContentBy(@PathVariable String id){
        return boxDetailsService.getContentByBoxID(id);
    }


}
