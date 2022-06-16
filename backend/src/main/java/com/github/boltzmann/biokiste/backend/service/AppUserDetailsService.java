package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppUserDetailsService {

    @Autowired
    private final EmailService emailService;
    private final AppUserDetailsRepo appUserDetailsRepo;
    private final BoxDetailsService boxDetailsService;


    @Autowired
    public AppUserDetailsService(
            AppUserDetailsRepo appUserDetailsRepo,
            BoxDetailsService boxDetailsService,
            EmailService emailService) {
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.boxDetailsService = boxDetailsService;
        this.emailService = emailService;
    }

    public List<OrganicBox> getSubscriptionsOfUser(String id){
        List<OrganicBox> boxesWithSameUserSubscribedAtLeastOneOrMoreTimes
                = boxDetailsService.getBoxesByUser(id);
        List<OrganicBox> allSubscribedBoxes = new ArrayList<>();
        for (OrganicBox box : boxesWithSameUserSubscribedAtLeastOneOrMoreTimes){
            Iterator<String> iter = box.getCustomers().iterator();
            while ( iter.hasNext() ) {
                if (id.equals(iter.next())) {
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

    public AppUserDto startUserEmailVerification(AppUser appUser) {

        emailService.sendMessage(appUser);
        return new ModelMapper().map(appUser, AppUserDto.class);
    }
}
