package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.dto.ItemDto;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.repository.ItemRepository;
import com.github.boltzmann.biokiste.backend.service.exceptions.NoSuchOrganicBoxItemException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemDetailsService {

    private final ItemRepository itemRepository;

    public ItemDetailsService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsById(List<String> content) {
        List<Item> items = new ArrayList<>();
        for (String itemId : content) {
            items.add(itemRepository.findById(itemId)
                    .orElseThrow(() -> new NoSuchOrganicBoxItemException("Item with ID " + itemId + " not found.")));
        }
        return items;
    }

    public Item addNewItem(ItemDto itemDto) {
        Item item = Item.builder().name(itemDto.getName()).build();
        return itemRepository.insert(item);
    }
}
