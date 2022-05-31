package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetContentOfBoxTest extends CrudTestWithLogIn{

    Item aepfel = Item.builder().id("1").name("Äpfel der Woche").build();
    Item fenchel = Item.builder().id("2").name("Fenchel").build();
    Item ruebstiel = Item.builder().id("3").name("Rübstiel").build();

    @Test
    void whenGetContentOfBoxWithValidID_thenListItemsInBoxReturned(){
        AppUser testuser = createTestUserInLoginRepoAndGet("1", "testuser", "passwort");
        String jwt = getTokenFor("testuser", "passwort");
        OrganicBox testusersBox = OrganicBox.builder()
                .id("1")
                .content(List.of(aepfel, fenchel))
                .build();
        boxRepository.insert(testusersBox);
        // When
        List<Item> actual = webTestClient.get()
                .uri("/api/box/1")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Item.class)
                .returnResult()
                .getResponseBody();
        // Then
        Assertions.assertEquals(List.of(aepfel, fenchel), actual);
    }
}
