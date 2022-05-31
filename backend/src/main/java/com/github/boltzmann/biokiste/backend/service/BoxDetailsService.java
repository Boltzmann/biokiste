package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.BoxRepository;
import com.github.boltzmann.biokiste.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoxDetailsService {

    private final BoxRepository boxRepository;
    private final ItemDetailsService itemDetailsService;

    @Autowired
    public BoxDetailsService(BoxRepository boxRepository, ItemDetailsService itemDetailsService) {
        this.boxRepository = boxRepository;
        this.itemDetailsService = itemDetailsService;
    }

    public List<OrganicBox> getBoxesByUser(String id){
        List<OrganicBox> list = boxRepository.findByCustomersIn(List.of(id));
        return list;
    }

    public List<Item> getContentByBoxID(String s) {
        OrganicBox box = boxRepository.findById(s)
                .orElseThrow(() -> new NoSuchElementException("There is no box with id " + s));
        return itemDetailsService.getItemsById(box.getContent());
    }
}
