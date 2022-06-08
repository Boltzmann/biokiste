package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.service.ItemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private final ItemDetailsService itemDetailsService;

    public ItemController(ItemDetailsService itemDetailsService) {
        this.itemDetailsService = itemDetailsService;
    }

    @GetMapping(path="/all")
    public List<Item> getAllItems(){
        return itemDetailsService.getAllItems();
    }
}
