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
        List<String> boxList = box.getCustomers();
        if(boxList != null) {
            boxList.add(userId);
            box.setCustomers(boxList);
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
        List<String> tmpBoxes = box.getCustomers();
        if(tmpBoxes != null) {
            tmpBoxes.remove(userId);
            box.setCustomers(tmpBoxes);
        }
        organicBoxRepository.save(box);
    }

    public OrganicBox addItemToBox(String boxId, String itemId) {
        OrganicBox box = findAndGetOrganicBox(boxId);
        List<String> tmpContent = box.getContent();
        if(tmpContent != null){
            tmpContent.add(itemId);
            box.setContent(tmpContent);
        } else {
            box.setContent(List.of(itemId));
        }
        organicBoxRepository.save(box);
        return box;
    }

    private OrganicBox findAndGetOrganicBox(String boxId) {
        return organicBoxRepository.findById(boxId)
                .orElseThrow(() -> new NoSuchOrganicBoxException(WARNING_NO_BOX + boxId));
    }

    public void deleteItemFromBoxContent(String boxId, String itemId) {
        OrganicBox box = findAndGetOrganicBox(boxId);
        List<String> tmpBoxes = box.getContent();
        if(tmpBoxes != null) {
            tmpBoxes.remove(itemId);
            box.setContent(tmpBoxes);
        }
        organicBoxRepository.save(box);
    }
}
