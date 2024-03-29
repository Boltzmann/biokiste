package com.github.boltzmann.biokiste.backend.security.controller;

import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import com.github.boltzmann.biokiste.backend.service.EmailService;
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

import java.time.Duration;

import static org.mockito.Mockito.*;

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

    EmailService emailService = mock(EmailService.class);

    @BeforeEach
    public void cleanUp(){
        appUserLoginRepository.deleteAll();
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(300000))
                .build();
    }

    AppUser testUser() {
        return AppUser.builder().username("Test User")
                .password("Pass word")
                .email("email@domain.org")
                .build();
    }

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

    @Test
    void testVerification_whenValidCredentials_thenReturnRegisterSuccess(){
        // When
        doNothing().when(emailService).sendMessage(any(AppUser.class));
        String actual = postVerificationData(testUser());
        // Then
        Assertions.assertEquals("register_success", actual);
    }

    @Test
    void testVerification_whenValidCredentials_thenCheckIfInRepo(){
        postVerificationData(testUser());
        AppUser actual = appUserLoginRepository.findByUsername(testUser().getUsername())
                .orElseThrow();
        Assertions.assertEquals(testUser().getEmail(), actual.getEmail());
        Assertions.assertFalse(actual.isVerified());
        Assertions.assertNotNull(actual.getId());
        Assertions.assertNotNull(actual.getVerificationId());
    }

    private String postVerificationData(AppUser body) {
        return webTestClient.post()
                .uri("/auth/sendVerificationMail")
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
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
