package com.github.boltzmann.biokiste.backend.security.controller;

import com.github.boltzmann.biokiste.backend.dto.AppUserDetails;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void getUserDetails_whenLoginOK_thenMeGetsNameAndCustomerId(){
        // Given
        AppUser testUser = createTestUserInRepoAndGet();

        String jwt = getTokenFrom(testUser);
        // When
        AppUserDetails actual = webTestClient.get()
                .uri("/api/user/me")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserDetails.class)
                .returnResult()
                .getResponseBody();
        // Then
        AppUserDetails expected = AppUserDetails.builder()
                .username("testuser")
                .customerId("1")
                .build();
        Assertions.assertEquals(expected, actual);
    }

    private String getTokenFrom(AppUser appUser){
        return webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUser.builder()
                        .username(appUser.getUsername())
                        .password(appUser.getPassword())
                        .build())
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    private AppUser createTestUserInRepoAndGet(){
        String hashedPassword = passwordEncoder.encode("passwort");
        AppUser testUser = AppUser.builder()
                .username("testuser")
                .password(hashedPassword)
                .customerId("1")
                .build();
        appUserRepository.save(testUser);
        return testUser;
    }
}
