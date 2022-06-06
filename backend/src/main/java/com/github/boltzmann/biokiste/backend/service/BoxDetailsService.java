package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.OrganicBoxRepository;
import com.github.boltzmann.biokiste.backend.service.exceptions.NoSuchOrganicBoxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoxDetailsService {

    public static final String WARNING_NO_BOX = "There is no box with id ";
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

    public List<Item> getContentByBoxID(String boxId) {
        OrganicBox box = findAndGetOrganicBox(boxId);
        return itemDetailsService.getItemsById(box.getContent());
    }

    public OrganicBox addSubscriptionOfUserToBox(String userId, String boxId) {
        OrganicBox box = findAndGetOrganicBox(boxId);
        List<String> oldBoxList = box.getCustomers();
        if(oldBoxList != null) {
            List<String> tmp = box.getCustomers();
            tmp.add(userId);
            box.setCustomers(tmp);
        } else {
            box.setCustomers(List.of(userId));
        }
        organicBoxRepository.save(box);
        return box;
    }

    public List<OrganicBoxDto> getAllBoxNamesAndId(){
        List<OrganicBoxDto> allBoxNamesAndId = new ArrayList<>();
        List<OrganicBox> organicBoxes = organicBoxRepository.findAll();
        organicBoxes.forEach(box ->
            allBoxNamesAndId.add(OrganicBoxDto.builder().id(box.getId()).name(box.getName()).build())
        );
        return allBoxNamesAndId;
    }

    public void removeSubscriptionOfUserOfBox(String userId, String boxId) {
        OrganicBox box = findAndGetOrganicBox(boxId);
        List<String> oldBoxList = box.getCustomers();
        if(oldBoxList != null) {
            oldBoxList.remove(userId);
            box.setCustomers(oldBoxList);
        }
        organicBoxRepository.save(box);
    }

    private OrganicBox findAndGetOrganicBox(String boxId) {
        return organicBoxRepository.findById(boxId)
                .orElseThrow(() -> new NoSuchOrganicBoxException(WARNING_NO_BOX + boxId));
    }
}
