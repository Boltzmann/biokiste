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
    Item wheat() { return Item.builder().id("2").name("Wheat").build(); };

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
    void addOneItemToBox(){
        itemRepository.insert(wheat());
        organicBoxRepository.insert(emptyBox());
        OrganicBox actual = putOrganicBox(emptyBox().getId(), wheat().getId());
        OrganicBox expected = emptyBox();
        expected.setContent(List.of(wheat().getId()));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addOtherItemToBox(){
        itemRepository.insert(wheat());
        organicBoxRepository.insert(emptyBox());
        OrganicBox actual = putOrganicBox(emptyBox().getId(), meat().getId());
        OrganicBox expected = emptyBox();
        expected.setContent(List.of(meat().getId()));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void removeItemFromBox(){
        itemRepository.insert(wheat());
        OrganicBox box = emptyBox();
        box.setContent(List.of(wheat().getId()));
        deleteFromOrganicBox(box.getId(), wheat().getId());
    }

    private void deleteFromOrganicBox(String boxId, String itemId) {
        webTestClient.put()
                .uri("/api/box/" + boxId + "/item/" + itemId)
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    private OrganicBox putOrganicBox(String boxId, String itemId) {
        return webTestClient.put()
                .uri("/api/box/" + boxId + "/item/" + itemId)
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(OrganicBox.class)
                .returnResult()
                .getResponseBody();
    }
}
