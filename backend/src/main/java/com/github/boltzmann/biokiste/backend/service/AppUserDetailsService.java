package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppUserDetailsService {

    private final AppUserDetailsRepo appUserDetailsRepo;
    private final BoxDetailsService boxDetailsService;


    @Autowired
    public AppUserDetailsService(
            AppUserDetailsRepo appUserDetailsRepo,
            BoxDetailsService boxDetailsService,
            EmailService emailService, AppUserLoginRepository appUserLoginRepository) {
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.boxDetailsService = boxDetailsService;
    }

    public List<OrganicBox> getSubscriptionsOfUser(String id){
        List<OrganicBox> boxesWithSameUserSubscribedAtLeastOneOrMoreTimes
                = boxDetailsService.getBoxesByUser(id);
        List<OrganicBox> allSubscribedBoxes = new ArrayList<>();
        for (OrganicBox box : boxesWithSameUserSubscribedAtLeastOneOrMoreTimes){
            for (String s : box.getCustomers()) {
                if (id.equals(s)) {
                    allSubscribedBoxes.add(box);
                }
            }
        }
        return allSubscribedBoxes;
    }

    public AppUserDetails getUserDetails(String id, String username) {
        if(appUserDetailsRepo.existsById(id)) {
            return appUserDetailsRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(
                            "Details of app user with " + id + " not found."));
        }
        return appUserDetailsRepo.insert(AppUserDetails.builder()
                .id(id)
                .username(username)
                .build());
    }
}
