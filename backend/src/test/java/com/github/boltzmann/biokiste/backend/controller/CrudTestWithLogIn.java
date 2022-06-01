package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.repository.OrganicBoxRepository;
import com.github.boltzmann.biokiste.backend.repository.ItemRepository;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrudTestWithLogIn {
    @Value("${piphi.biokisteapp.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserLoginRepository appUserLoginRepository;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    OrganicBoxRepository organicBoxRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AppUserDetailsRepo appUserDetailsRepo;

    @BeforeEach
    public void cleanUp(){
        itemRepository.deleteAll();
        organicBoxRepository.deleteAll();
        appUserLoginRepository.deleteAll();
        appUserDetailsRepo.deleteAll();
    }

    String getTokenFor(String username, String password) {
        return webTestClient.post()
                .uri("/auth/login")
                .bodyValue(AppUser.builder()
                        .username(username)
                        .password(password)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    AppUser createTestUserInLoginRepoAndGet(String id, String name, String password){
        String hashedPassword = passwordEncoder.encode(password);
        AppUser testUser = AppUser.builder()
                .id(id)
                .username(name)
                .password(hashedPassword)
                .build();
        appUserLoginRepository.save(testUser);
        return testUser;
    }
}
