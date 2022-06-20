package com.github.boltzmann.biokiste.backend.security.service;

import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import com.github.boltzmann.biokiste.backend.service.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class VerificationService {
    private final AppUserLoginRepository appUserLoginRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public VerificationService(AppUserLoginRepository appUserLoginRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.appUserLoginRepository = appUserLoginRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public String register(AppUser appUser){
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        String randomCode = RandomStringUtils.randomAlphanumeric(10);
        appUser.setVerificationId(randomCode);
        appUser.setVerified(false);

        appUserLoginRepository.insert(appUser);

        emailService.sendMessage(appUser);
        return "register_success";
    }

    public boolean isVerified( AppUser maybeAppUser ) {
        AppUser appUserFromRepo = appUserLoginRepository.findByUsername(maybeAppUser.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User " + maybeAppUser.getUsername() + " not found."));
        if( !maybeAppUser.getVerificationId().equals(appUserFromRepo.getVerificationId())){
            return false;
        }
        appUserFromRepo.setVerified(true);
        appUserLoginRepository.save(appUserFromRepo);
        return true;
    }
}
