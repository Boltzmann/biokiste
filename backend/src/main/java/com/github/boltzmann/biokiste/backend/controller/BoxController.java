package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
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

    @PutMapping(path="{boxId}/item/{itemId}")
    public OrganicBox putItemToBoxContent(@PathVariable String boxId, @PathVariable String itemId){
        return OrganicBox.builder().id("7").content(List.of("2")).customers(List.of("4711")).build();
    }
}
