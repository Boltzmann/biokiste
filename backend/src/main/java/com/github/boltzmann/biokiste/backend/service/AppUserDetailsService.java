package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppUserDetailsService {

    @Autowired
    private final JavaMailSender javaMailSender;
    private final AppUserDetailsRepo appUserDetailsRepo;
    private final BoxDetailsService boxDetailsService;
    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    @Autowired
    public AppUserDetailsService(
            AppUserDetailsRepo appUserDetailsRepo,
            BoxDetailsService boxDetailsService,
            JavaMailSender javaMailSender
    ) {
        this.appUserDetailsRepo = appUserDetailsRepo;
        this.boxDetailsService = boxDetailsService;
        this.javaMailSender = javaMailSender;
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
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(SENDER_EMAIL);
        msg.setTo(appUser.getEmail());

        msg.setSubject(appUser.getUsername());
        msg.setText("Hello " + appUser.getUsername() + "\n AppUserVerificationDetailService");

        javaMailSender.send(msg);
        return new ModelMapper().map(appUser, AppUserDto.class);
    }
}
