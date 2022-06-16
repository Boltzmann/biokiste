package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppUserDetailsService {

    @Autowired
    private final EmailService emailService;
    private final AppUserDetailsRepo appUserDetailsRepo;
    private final AppUserLoginRepository appUserLoginRepository;
    private final BoxDetailsService boxDetailsService;


    @Autowired
    public AppUserDetailsService(
            AppUserDetailsRepo appUserDetailsRepo,
            BoxDetailsService boxDetailsService,
            EmailService emailService, AppUserLoginRepository appUserLoginRepository) {
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.boxDetailsService = boxDetailsService;
        this.emailService = emailService;
        this.appUserLoginRepository = appUserLoginRepository;
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

    public AppUserDto startUserEmailVerification(AppUserDto appUserDto) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<AppUser> optAppUser = appUserLoginRepository.findByUsername(appUserDto.getUsername());
        if( !optAppUser.isPresent() ){
            appUserDto.setVerificationCode(RandomStringUtils.randomAlphanumeric(10));
            AppUser newAppUser = modelMapper.map(appUserDto, AppUser.class);
            appUserLoginRepository.insert(newAppUser);
            emailService.sendMessage(newAppUser);
            return appUserDto;
        } else {
            AppUser appUser = optAppUser.get();
            if( !appUser.isVerified() ){
                emailService.sendMessage(appUser);
            }
            return modelMapper.map(appUser, AppUserDto.class);
        }
    }
}
