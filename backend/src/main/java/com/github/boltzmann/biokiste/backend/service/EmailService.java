package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String SENDER_EMAIL;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(AppUserDto appUserDto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(SENDER_EMAIL);
        msg.setTo(appUserDto.getEmail());

        msg.setSubject(appUserDto.getUsername());
        msg.setText("Hello " + appUserDto.getUsername() + "\n AppUserVerificationDetailService");

        javaMailSender.send(msg);
    }
}
