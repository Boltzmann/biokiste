package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemControllerTest extends CrudTestWithLogIn {

    Item one() { return Item.builder().id("1").build(); };
    Item two() { return Item.builder().id("2").build(); };

    @Test
    void getAllItemsTest(){
        AppUser user = createTestUserInLoginRepoAndGet("42", "The User", "GEHEIM");
        String jwt = getTokenFor("The User", "GEHEIM");
        itemRepository.insert(one());
        List<Item> actual = webTestClient.get()
                .uri("/api/item/all")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Item.class)
                .returnResult()
                .getResponseBody();
        List<Item> expected = List.of(one(), two());
        Assertions.assertEquals(expected, actual);
    }
}
