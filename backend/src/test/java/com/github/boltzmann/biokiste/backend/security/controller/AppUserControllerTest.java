package com.github.boltzmann.biokiste.backend.security.controller;

import com.github.boltzmann.biokiste.backend.dto.AppUserDetails;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppUserControllerTest {
    @Value("${piphi.biokisteapp.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserDetailsRepo appUserDetailsRepo;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void cleanUp(){ appUserRepository.deleteAll();}

    @Test
    void login_whenValidCredentials_thenReturnValidJWTAndGetUserDetails(){
        // Given
        AppUser testUser = createTestUserInRepoAndGet();
        String jwt = webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUser.builder()
                        .username("testuser")
                        .password("passwort")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(jwt);
        // When
        webTestClient.get()
                .uri("/api/user/me")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserDetails.class)
                .returnResult()
                .getResponseBody();
        AppUserDetails userToChange = new AppUserDetails();
        try{
             AppUser tmpUser = appUserRepository.findByUsername("testuser")
                     .orElseThrow();
             userToChange = appUserDetailsRepo.findById(tmpUser.getId()).orElseThrow();
        } catch (Exception ex){
            Assertions.fail();
        }
        userToChange.setCustomerId("1");
        appUserDetailsRepo.save(userToChange);
        AppUserDetails actual = webTestClient.get()
                .uri("/api/user/me")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserDetails.class)
                .returnResult()
                .getResponseBody();
        // Then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("testuser", actual.getUsername());
        Assertions.assertEquals("1", actual.getCustomerId());
    }

    private AppUser createTestUserInRepoAndGet(){
        String hashedPassword = passwordEncoder.encode("passwort");
        AppUser testUser = AppUser.builder()
                .username("testuser")
                .password(hashedPassword)
                .build();
        appUserRepository.save(testUser);
        return testUser;
    }
}
