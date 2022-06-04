package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

class GetDifferentBoxesTest extends CrudTestWithLogIn {

    private OrganicBox organicBox() {
        return OrganicBox.builder()
                .id("1")
                .customers(new ArrayList<>(List.of("666")))
                .build();
    }

    @Test
    void whenGetAllOrganicBoxes_thenListOfOrganicBoxesReturned(){
        // Given
        createTestUserInLoginRepoAndGet("666", "testuser", "passwort");
        String jwt = getTokenFor("testuser", "passwort");
        organicBoxRepository.insert(organicBox());
        Assertions.assertEquals(List.of(organicBox()), organicBoxRepository.findByCustomersIn(List.of("666")));
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
        Assertions.assertEquals(List.of(organicBox()), actual);
    }

    @Test
    void whenGetAllOrganicBoxesOfOtherUser_thenListOfHisOrganicBoxesNamesList(){
        // Given
        createTestUserInLoginRepoAndGet("666", "Test User", "passwort");
        createTestUserInLoginRepoAndGet("42", "Other User", "GEHEIM");
        String jwtOtheruser = getTokenFor("Other User", "GEHEIM");
        String jwtTestuser = getTokenFor("Test User", "passwort");
        int weekOfYear = LocalDate.of(2022, 5, 30).get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        List<String> exampleList = List.of("1", "2");
        List<String> otherList = List.of("3");
        OrganicBox organicBox = OrganicBox.builder()
                .weekOfYear(weekOfYear)
                .id("1")
                .customers(List.of("42", "666"))
                .content(exampleList).build();
        OrganicBox otherBox = OrganicBox.builder()
                .weekOfYear(weekOfYear)
                .id("3")
                .customers(List.of("42"))
                .content(otherList).build();
        organicBoxRepository.insert(organicBox);
        organicBoxRepository.insert(otherBox);

        // When
        List<OrganicBox> actual = webTestClient.get()
                .uri("/api/user/subscribedBoxes")
                .headers(http -> http.setBearerAuth(jwtOtheruser))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        List<OrganicBox> checkAlso = webTestClient.get()
                .uri("/api/user/subscribedBoxes")
                .headers(http -> http.setBearerAuth(jwtTestuser))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        // Then
        Assertions.assertEquals(List.of(organicBox, otherBox), actual);
        Assertions.assertEquals(List.of(organicBox), checkAlso);
    }

    @Test
    void whenGetAllOrganicBoxesOfOtherUser_thenListOfHisOrganicBoxesNamesListEvenIfItSubscribeOneTwice(){
        // Given
        createTestUserInLoginRepoAndGet("42", "Other User", "GEHEIM");
        String jwtOtheruser = getTokenFor("Other User", "GEHEIM");
        OrganicBox organicBox = OrganicBox.builder()
                .id("1")
                .customers(List.of("42", "42", "666", "4711"))
                .build();
        OrganicBox otherBox = OrganicBox.builder()
                .id("3")
                .customers(List.of("42"))
                .build();
        organicBoxRepository.insert(organicBox);
        organicBoxRepository.insert(otherBox);

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
        Assertions.assertEquals(List.of(organicBox, organicBox, otherBox), actual);
    }

}
