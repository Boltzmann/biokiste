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
    void postBox_whenOtherValidUserIsAddedToBox_then200AndGetUpdatedBox(){
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
    void postBox_whenThereIsNoCustomerInBox_AddNewCustomerWith200(){
        // Given
        createTestUserInLoginRepoAndGet("23", "New User" , "Pass the wort!");
        String jwt = getTokenFor("New User", "Pass the wort!");
        OrganicBox lonelyBox = OrganicBox.builder()
                .id("38501")
                .build();
        organicBoxRepository.insert(lonelyBox);
        // When
        OrganicBox actual = getPostedOrganicBox(jwt, "38501");
        // Then
        OrganicBox happyBox = OrganicBox.builder()
                .id("38501")
                .customers(List.of("23"))
                .build();
        Assertions.assertEquals(happyBox, actual);
    }

    @Test
    void postBox_whenUserAddsItselfSecondTime_thenThereAreTwoUsersSubscrbedToBox(){
        // Given
        createTestUserInLoginRepoAndGet("22", "Hungry User" , "Bibidi!");
        String jwt = getTokenFor("Hungry User", "Bibidi!");
        organicBoxRepository.insert(box);
        // When
        getPostedOrganicBox(jwt, "7");
        OrganicBox actual = getPostedOrganicBox(jwt, "7");
        // Then
        box.setCustomers(List.of("4711","22","22"));
        Assertions.assertEquals(box, actual);
    }

    @Test
    void postBox_whenValidUserIsAddedToNotExistingBox_then500(){
        // Given
        createTestUserInLoginRepoAndGet("42", "Test User" , "Pass the wort!");
        String jwt = getTokenFor("Test User", "Pass the wort!");
        // When and then error
        webTestClient.post()
                .uri("api/user/subscribeBox")
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue("7")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void postBox_whenNoCustomerFieldAtBoxInDB_then200AndAddCustomerFieldAndThereIsOneUserSubscribedToBox(){
        // Given
        createTestUserInLoginRepoAndGet("42", "Test User" , "Pass the wort!");
        String jwt = getTokenFor("Test User", "Pass the wort!");
        OrganicBox specialBox = OrganicBox.builder()
                .id("0")
                .build();
        organicBoxRepository.insert(specialBox);
        // When
        OrganicBox actual = getPostedOrganicBox(jwt, "0");
        // Then
        specialBox.setCustomers(List.of("42"));
        Assertions.assertEquals(specialBox, actual);
    }

    private OrganicBox getPostedOrganicBox(String jwt, String boxId) {
        OrganicBox actual = webTestClient.post()
                .uri("api/user/subscribeBox")
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue(boxId)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        return actual;
    }

}
