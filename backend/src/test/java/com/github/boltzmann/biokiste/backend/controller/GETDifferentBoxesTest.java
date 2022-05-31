package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;


public class GETDifferentBoxesTest extends CrudTestWithLogIn {

    Item aepfel = Item.builder().id("1").name("Äpfel der Woche").build();
    Item fenchel = Item.builder().id("2").name("Fenchel").build();
    Item ruebstiel = Item.builder().id("3").name("Rübstiel").build();

    @Test
    void whenGetAllOrganicBoxes_thenListOfOrganicBoxesReturned(){
        // Given
        AppUser testuser = createTestUserInLoginRepoAndGet("1", "testuser", "passwort");
        String jwt = getTokenFor("testuser", "passwort");
        int weekOfYear = LocalDate.of(2022, 5, 30).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        List<Item> exampleList = List.of(aepfel, fenchel);
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
        String jwtOtheruser = getTokenFor("Other User", "passwort");
        int weekOfYear = LocalDate.of(2022, 5, 30).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        List<Item> exampleList = List.of(aepfel, fenchel);
        List<Item> otherList = List.of(ruebstiel);
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



}
