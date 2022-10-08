package com.github.boltzmann.biokiste.backend.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VerySimpleSendingARealMailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void verificationTest() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("earl_of_oelde@web.de");
        msg.setTo("stefan.bollmann@rwth-aachen.de");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

        Assertions.assertTrue(true);
    }
}
