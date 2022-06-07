package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/item")
public class ItemController {

    @GetMapping(path="/all")
    public List<Item> getAllItems(){
        return List.of(Item.builder().id("1").build(), Item.builder().id("2").build());
    }
}
