package com.github.boltzmann.biokiste.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class BackendApplication {
    @Autowired
    private JavaMailSender javaMailSender;
    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);
    }

}
