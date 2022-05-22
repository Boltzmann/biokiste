package com.github.boltzmann.biokiste.backend.security.controller;

import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppUserAuthControllerTest {
    @Value("${piphi.biokisteapp.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserLoginRepository appUserLoginRepository;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void cleanUp(){ appUserLoginRepository.deleteAll();}

    @Test
    void login_whenValidCredentials_thenReturnValidJWT(){
        // Given
        AppUser testUser = createTestUserInRepoAndGet();
        //When
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
        // Then
        String expected = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
        Assertions.assertEquals(expected, testUser.getUsername());
    }

    @Test
    void login_whenInvalidCredentials_thenReturnForbiddenError() {
        //given
        createTestUserInRepoAndGet();

        //when//then
        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUser.builder()
                        .username("testuser")
                        .password("wrong-password")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }


    private AppUser createTestUserInRepoAndGet(){
        String hashedPassword = passwordEncoder.encode("passwort");
        AppUser testUser = AppUser.builder()
                .username("testuser")
                .password(hashedPassword)
                .build();
        appUserLoginRepository.save(testUser);
        return testUser;
    }
}
