package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.security.service.AppUserLoginDetailsService;
import com.github.boltzmann.biokiste.backend.service.AppUserDetailsService;
import com.github.boltzmann.biokiste.backend.service.AppUserVerificationDetailsService;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user/")
public class AppUserController {

    private final BoxDetailsService boxDetailsService;
    private final AppUserLoginDetailsService appUserLoginDetailsService;
    private final AppUserDetailsService appUserDetailsService;

    private final AppUserVerificationDetailsService appUserVerificationDetailsService;

    @Autowired
    public AppUserController(
            BoxDetailsService boxDetailsService,
            AppUserLoginDetailsService appUserLoginDetailsService,
            AppUserDetailsService appUserDetailsService,
            JavaMailSender javaMailSender, AppUserVerificationDetailsService appUserVerificationDetailsService) {
        this.boxDetailsService = boxDetailsService;
        this.appUserLoginDetailsService = appUserLoginDetailsService;
        this.appUserDetailsService = appUserDetailsService;
        this.appUserVerificationDetailsService = appUserVerificationDetailsService;
    }

    @GetMapping("/me")
    public AppUserDetails getLoggedInUserDetails(Principal principal){
        String id = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        appUserVerificationDetailsService.sendSimpleMessage();
        return appUserDetailsService.getUserDetails(id, principal.getName());
    }

    @GetMapping("/subscribedBoxes")
    public List<OrganicBox> getBoxesByUser(Principal principal){
        String id = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        return appUserDetailsService.getSubscriptionsOfUser(id);
    }

    @PostMapping(path = "/subscribeBox")
    public OrganicBox subscribeBox(Principal principal, @RequestBody String boxId){
        String userId = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        return boxDetailsService.addSubscriptionOfUserToBox(userId, boxId);
    }
    @DeleteMapping("/subscribeBox/{boxId}")
    public void removeFromBox(@PathVariable String boxId, Principal principal){
        String userId = this.appUserLoginDetailsService
                .getUserIdByName(principal.getName());
        boxDetailsService.removeSubscriptionOfUserOfBox(userId, boxId);
    }
}
