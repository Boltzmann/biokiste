package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.ItemDto;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.service.ItemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/all")
    public List<Item> getAllItems() {
        return itemDetailsService.getAllItems();
    }

    @PostMapping()
    public Item createNewItem(@RequestBody ItemDto itemDto) {
        return itemDetailsService.addNewItem(itemDto);
    }

    @PutMapping("/{id}")
    public Item putItem(@RequestBody ItemDto itemDto, @PathVariable String id) {
        Item itemToChange = Item.builder()
                .id(id)
                .name(itemDto.getName())
                .build();
        return itemDetailsService.changeItem(itemToChange);
    }
}
