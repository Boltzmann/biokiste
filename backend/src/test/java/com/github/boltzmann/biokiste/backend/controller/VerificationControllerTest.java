package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.service.AppUserVerificationDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;


class VerificationControllerTest extends CrudTestWithLogIn{

    AppUserVerificationDetailsService appUserVerificationDetailsService =
            new AppUserVerificationDetailsService(javaMailSender);

    @Test
    void verificationTest() throws MessagingException, UnsupportedEncodingException {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("earl_of_oelde@web.de");
        msg.setTo("stefan.bollmann@rwth-aachen.de");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

        Assertions.assertTrue(true);
    }
}
