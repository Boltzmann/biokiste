package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    // ts-lint
    private String SENDER_EMAIL;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(AppUser appUser) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(SENDER_EMAIL);
        msg.setTo(appUser.getEmail());

        msg.setSubject(appUser.getUsername());
        msg.setText("Hello " +
                appUser.getUsername() + "\n "
                + "https://biokiste.herokuapp.com/#/login/" + appUser.getVerificationId() + " <- click here to verify.");

        javaMailSender.send(msg);
    }
}
