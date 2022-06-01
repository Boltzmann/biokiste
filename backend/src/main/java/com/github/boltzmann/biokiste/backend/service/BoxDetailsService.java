package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.OrganicBoxRepository;
import com.github.boltzmann.biokiste.backend.service.exceptions.NoSuchOrganicBoxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxDetailsService {

    private final OrganicBoxRepository organicBoxRepository;
    private final ItemDetailsService itemDetailsService;

    @Autowired
    public BoxDetailsService(OrganicBoxRepository organicBoxRepository, ItemDetailsService itemDetailsService) {
        this.organicBoxRepository = organicBoxRepository;
        this.itemDetailsService = itemDetailsService;
    }

    public List<OrganicBox> getBoxesByUser(String id){
        return organicBoxRepository.findByCustomersIn(List.of(id));
    }

    public List<Item> getContentByBoxID(String s) {
        OrganicBox box = organicBoxRepository.findById(s)
                .orElseThrow(() -> new NoSuchOrganicBoxException("There is no box with id " + s));
        return itemDetailsService.getItemsById(box.getContent());
    }
}
