package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ItemDetailsService {

    private final ItemRepository itemRepository;

    public ItemDetailsService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsById(List<String> content) {
        List<Item> items = new ArrayList<>();
        for (String itemId : content) {
            items.add(itemRepository.findById(itemId)
                    .orElseThrow(() -> new NoSuchElementException("Item with ID " + itemId + "not found.")));
        }
        return items;
    }
}
