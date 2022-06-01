package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
public class PostBoxWithNewUserTest extends CrudTestWithLogIn{

    OrganicBox box = OrganicBox.builder()
            .id("7")
            .content(List.of("1", "2"))
            .customers(List.of("4711"))
            .build();

    @Test
    void PostBox_whenValidUserIsAddedToBox_then200AndGetUpdatedBox(){
        // Todo: or better a list of subscribed boxes?
        // Given
        createTestUserInLoginRepoAndGet("42", "Test User" , "Pass the wort!");
        String jwt = getTokenFor("Test User", "Pass the wort!");
        organicBoxRepository.insert(box);
        // When
        OrganicBox actual = webTestClient.post()
                .uri("api/user/subscribeBox/7")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        // Then
        OrganicBox expected = OrganicBox.builder()
                .id("7")
                .content(List.of("1", "2"))
                .customers(List.of("4711", "42"))
                .build();
        Assertions.assertEquals(expected, actual);
    }
}
