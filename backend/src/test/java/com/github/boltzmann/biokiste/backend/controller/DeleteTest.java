package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DeleteTest extends CrudTestWithLogIn {

    private final OrganicBox box() {
        return OrganicBox.builder()
                .id("7")
                .customers(List.of("4711", "666"))
                .build();
    }


    @Test
    void deleteUserFromBoxTest(){
        // Given
        createTestUserInLoginRepoAndGet("666", "Test User", "Passwort");
        String jwt = getTokenFor("Test User", "Passwort");
        organicBoxRepository.insert(box());
        // When
        webTestClient.delete()
                .uri("/api/user/subscribeBox/7")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful();
        OrganicBox actual = organicBoxRepository.findById("7").orElseThrow();
        // Then
        OrganicBox expected = box();
        expected.setCustomers(List.of("4711"));
        Assertions.assertEquals(expected, actual);
    }
}
