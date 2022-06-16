package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class AppUserVerificationDetailsService {
    @Autowired
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    public AppUserVerificationDetailsService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
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
