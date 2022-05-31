package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.repository.BoxRepository;
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


    Item äpfel = Item.builder().id("1").name("Äpfel der Woche").build();
    Item fenchel = Item.builder().id("2").name("Fenchel").build();
    Item rübstiel = Item.builder().id("3").name("Rübstiel").build();

    @BeforeEach
    public void cleanUp(){
        boxRepository.deleteAll();
        appUserLoginRepository.deleteAll();
        appUserDetailsRepo.deleteAll();
    }


    @Test
    void whenGetAllOrganicBoxes_thenListOfOrganicBoxesReturned(){
        // Given
        AppUser testuser = createTestUserInLoginRepoAndGet("1", "testuser", "passwort");
        String jwt = getTokenFor("testuser", "passwort");
        int weekOfYear = LocalDate.of(2022, 05, 30).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        List<Item> exampleList = List.of(äpfel, fenchel);
        OrganicBox organicBox = OrganicBox.builder()
                .weekOfYear(weekOfYear)
                .id("1")
                .content(exampleList).build();
        boxRepository.insert(organicBox);
                appUserDetailsRepo.save(
                        new AppUserDetails().builder()
                                .id(testuser.getId())
                                .username(testuser.getUsername())
                                .subscribedBoxes(List.of(organicBox))
                                .build()
        );
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

    @Test
    void whenGetAllOrganicBoxesOfOtherUser_thenListOfHisOrganicBoxesReturned(){
        // Given
        AppUser testuser = createTestUserInLoginRepoAndGet("1", "Test User", "passwort");
        AppUser otheruser = createTestUserInLoginRepoAndGet("2", "Other User", "passwort");
        String jwtTestuser = getTokenFor("Test User", "passwort");
        String jwtOtheruser = getTokenFor("Other User", "passwort");
        int weekOfYear = LocalDate.of(2022, 05, 30).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        List<Item> exampleList = List.of(äpfel, fenchel);
        List<Item> otherList = List.of(rübstiel);
        OrganicBox organicBox = OrganicBox.builder()
                .weekOfYear(weekOfYear)
                .id("1")
                .content(exampleList).build();
        OrganicBox otherBox = OrganicBox.builder()
                .weekOfYear(weekOfYear)
                .id("3")
                .content(otherList).build();
        boxRepository.insert(organicBox);
        boxRepository.insert(otherBox);
        appUserDetailsRepo.save(
                new AppUserDetails().builder()
                        .id(testuser.getId())
                        .username(testuser.getUsername())
                        .subscribedBoxes(List.of(organicBox))
                        .build()
        );
        appUserDetailsRepo.save(
                new AppUserDetails().builder()
                        .id(otheruser.getId())
                        .username(otheruser.getUsername())
                        .subscribedBoxes(List.of(organicBox, otherBox))
                        .build()
        );
        // When
        List<OrganicBox> actual = webTestClient.get()
                .uri("/api/user/subscribedBoxes")
                .headers(http -> http.setBearerAuth(jwtOtheruser))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        // Then
        Assertions.assertEquals(List.of(organicBox, otherBox), actual);
    }

    private String getTokenFor(String username, String password) {
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

    private AppUser createTestUserInLoginRepoAndGet(String id, String name, String password){
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
