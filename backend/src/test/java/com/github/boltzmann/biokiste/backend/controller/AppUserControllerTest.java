package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserControllerTest {
    @Value("${piphi.biokisteapp.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserLoginRepository appUserLoginRepository;

    @Autowired
    AppUserDetailsRepo appUserDetailsRepo;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void cleanUp(){ appUserLoginRepository.deleteAll();}

    @Test
    void login_whenValidCredentials_thenReturnValidJWTAndGetUserDetails(){
        // Given
        AppUser testUser = createTestUserInLoginRepoAndGet();
        String jwt = getTokenForTestuser();
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
             AppUser tmpUser = appUserLoginRepository.findByUsername("testuser")
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

    private String getTokenForTestuser() {
        return webTestClient.post()
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
    }

    private AppUser createTestUserInLoginRepoAndGet(){
        String hashedPassword = passwordEncoder.encode("passwort");
        AppUser testUser = AppUser.builder()
                .username("testuser")
                .password(hashedPassword)
                .build();
        appUserLoginRepository.save(testUser);
        return testUser;
    }

    private AppUserDetails createUserDetailsInRepoAndGet(AppUser appUser, String customerId){
        AppUserDetails appUserDetails = AppUserDetails.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .customerId(customerId)
                .build();
        appUserDetailsRepo.insert(appUserDetails);
        return appUserDetails;
    }
}
