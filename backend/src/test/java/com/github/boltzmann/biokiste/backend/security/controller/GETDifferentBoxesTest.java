package com.github.boltzmann.biokiste.backend.security.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.repository.BoxRepository;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GETDifferentBoxesTest {
    @Value("${piphi.biokisteapp.jwt.secret}")
    private String jwtSecret;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserLoginRepository appUserLoginRepository;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BoxRepository boxRepository;

    @Autowired
    AppUserDetailsRepo appUserDetailsRepo;

    @Test
    void whenGetAllOrganicBoxes_thenListOfOrganicBoxesReturned(){
        // Given
        AppUser testUser = createTestUserInLoginRepoAndGet();
        String jwt = getTokenForTestuser();
        int weekOfYear = LocalDate.of(2022, 05, 30).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        Item äpfel = Item.builder().id("1").name("Äpfel der Woche").build();
        Item fenchel = Item.builder().id("2").name("Fenchel").build();
        List<Item> exampleList = new ArrayList<Item>();
        exampleList.add(äpfel);
        exampleList.add(fenchel);
        OrganicBox organicBox = OrganicBox.builder()
                .weekOfYear(weekOfYear)
                .id("1")
                .name("Mixkiste")
                .size("small")
                .content(exampleList).build();
        boxRepository.insert(organicBox);
        webTestClient.get()
                .uri("/api/user/me")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserDetails.class)
                .returnResult()
                .getResponseBody();
        AppUserDetails userToChange = new AppUserDetails();
        userToChange.setSubscribedBoxes(List.of(organicBox));
        appUserDetailsRepo.save(userToChange);
        // When
        List<OrganicBox> actual = webTestClient.get()
                .uri("/api/user/subscribedBoxes")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        // Then
        Assertions.assertEquals(List.of(organicBox), actual);
    }

    //ToDo: get boxes of specific user. Therefore write test in AppUserControllerTest to check additional parameter Subscriptions.

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
                .id("1")
                .username("testuser")
                .password(hashedPassword)
                .build();
        appUserLoginRepository.save(testUser);
        return testUser;
    }
}
