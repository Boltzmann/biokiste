package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
class PostBoxWithNewUserTest extends CrudTestWithLogIn{

    private final OrganicBox box = OrganicBox.builder()
            .id("7")
            .customers(List.of("4711"))
            .build();

    @Test
    void PostBox_whenOtherValidUserIsAddedToBox_then200AndGetUpdatedBox(){
        // Given
        createTestUserInLoginRepoAndGet("-1", "Other User" , "Pass the wort!");
        String jwt = getTokenFor("Other User", "Pass the wort!");
        organicBoxRepository.insert(box);
        // When
        OrganicBox actual = getPostedOrganicBox(jwt, "7");
        // Then
        OrganicBox expected = OrganicBox.builder()
                .id("7")
                .customers(List.of("4711", "-1"))
                .build();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void PostBox_whenValidUserIsAddedToBox_then200AndGetUpdatedBox(){
        // Todo: or better a list of subscribed boxes?
        // Given
        createTestUserInLoginRepoAndGet("42", "Test User" , "Pass the wort!");
        String jwt = getTokenFor("Test User", "Pass the wort!");
        organicBoxRepository.insert(box);
        // When
        OrganicBox actual = getPostedOrganicBox(jwt, "7");
        // Then
        OrganicBox expected = OrganicBox.builder()
                .id("7")
                .customers(List.of("4711", "42"))
                .build();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void PostBox_whenValidUserIsAddedToNotExistingBox_then500(){
        // Given
        createTestUserInLoginRepoAndGet("42", "Test User" , "Pass the wort!");
        String jwt = getTokenFor("Test User", "Pass the wort!");
        // When and then
        webTestClient.post()
                .uri("api/user/subscribeBox")
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue("7")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private OrganicBox getPostedOrganicBox(String jwt, String boxId) {
        OrganicBox actual = webTestClient.post()
                .uri("api/user/subscribeBox")
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue( boxId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        return actual;
    }
}
