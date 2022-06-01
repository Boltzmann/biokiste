package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GetContentOfBoxTest extends CrudTestWithLogIn{

    Item aepfel = Item.builder().id("1").name("Äpfel der Woche").build();
    Item fenchel = Item.builder().id("2").name("Fenchel").build();

    @Test
    void whenGetContentOfBoxWithValidID_thenListItemsInBoxReturned(){
        createTestUserInLoginRepoAndGet("1", "testuser", "passwort");
        String jwt = getTokenFor("testuser", "passwort");
        itemRepository.insert(aepfel);
        itemRepository.insert(fenchel);
        OrganicBox testusersBox = OrganicBox.builder()
                .id("1")
                .content(List.of("1", "2"))
                .build();
        organicBoxRepository.insert(testusersBox);
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

    @Test
    void whenGetContentOfBoxWithInvalidID_thenNoSuchOrganicBoxException(){
        String jwt = createUserInLoginRepoAndGetItsToken("1","testuser", "passwort");
        // When and then
        webTestClient.get()
                .uri("/api/box/666")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void whenGetItemOfBoxWithInvalidID_thenNoSuchOrganicBoxItemException(){
        String jwt = createUserInLoginRepoAndGetItsToken("1","testuser", "passwort");
        OrganicBox testusersBox = OrganicBox.builder()
                .id("1")
                .content(List.of("1"))
                .build();
        organicBoxRepository.insert(testusersBox);
        // When and then
        webTestClient.get()
                .uri("/api/box/1")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    String createUserInLoginRepoAndGetItsToken(String id, String username, String password){
        createTestUserInLoginRepoAndGet(id, username, password);
        return getTokenFor(username, password);
    }
}
