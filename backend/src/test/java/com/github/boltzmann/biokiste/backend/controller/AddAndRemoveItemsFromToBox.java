package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
class AddAndRemoveItemsFromToBoxTest extends CrudTestWithLogIn{


    private String jwt;

    Item meat() { return Item.builder().id("1").name("Meat").build(); };
    Item wheat() { return Item.builder().id("1").name("Wheat").build(); };

    private final OrganicBox emptyBox() {
        return OrganicBox.builder()
                .id("7")
                .customers(List.of("4711"))
                .build();
    }

    @BeforeEach
    private void createUserInLoginRepoAndGetTokenForHer(){
        createTestUserInLoginRepoAndGet("42", "The User", "GEHEIM");
        jwt = getTokenFor("The User", "GEHEIM");
    }

    @Test
    void addItemToBox(){
        itemRepository.insert(wheat());
        organicBoxRepository.insert(emptyBox());
        OrganicBox actual = webTestClient.put()
                .uri("/api/box/" + emptyBox().getId() + "/item/" + wheat().getId())
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(OrganicBox.class)
                .returnResult()
                .getResponseBody();
        OrganicBox expected = emptyBox();
        expected.setContent(List.of(wheat().getId()));
        Assertions.assertEquals(expected, actual);
    }
}
