package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.ItemDto;
import com.github.boltzmann.biokiste.backend.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemControllerTest extends CrudTestWithLogIn {

    String jwt;

    Item one() { return Item.builder().id("1").name("one").build(); };
    Item two() { return Item.builder().id("2").name("two").build(); };

    @BeforeEach
    private void createUserInLoginRepoAndGetTokenForHer(){
        createTestUserInLoginRepoAndGet("42", "The User", "GEHEIM");
        jwt = getTokenFor("The User", "GEHEIM");
    }

    @Test
    void getAllItemsTest_whenOneInRepo_thenGetListOfOneBox(){
        itemRepository.insert(one());
        List<Item> actual = getAllItems(jwt);
        List<Item> expected = List.of(one());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllItemsTest_whenNoItemsInRepo_getEmptyList(){
        List<Item> actual = getAllItems(jwt);
        Assertions.assertEquals(List.of(), actual);
    }

    @Test
    void createNewItemTest_whenNewItemInBlankRepoPosted_getNewItemAndItemIsInRepo(){
        createUserInLoginRepoAndGetTokenForHer();
        ItemDto dto = ItemDto.builder().name(one().getName()).build();
        Item actual = createNewItem(jwt, dto);
        Assertions.assertEquals(one().getName(), actual.getName());
    }

    @Test
    void createNewItemTest_whenOtherItemInBlankRepoPosted_getNewItemAndItemIsInRepo(){
        createUserInLoginRepoAndGetTokenForHer();
        ItemDto dto = ItemDto.builder().name(two().getName()).build();
        Item actual = createNewItem(jwt, dto);
        Assertions.assertEquals(two().getName(), actual.getName());
    }

    @Test
    void createNewItemTest_whenItemAlreadyInRepoPosted_getErrorItemNotAdded(){
        ItemDto dto = ItemDto.builder().name(two().getName()).build();
        itemRepository.insert(Item.builder().id("1").name("two").build());
        // When
        webTestClient.post()
                .uri("/api/item")
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void putItemTest_whenNewNameIsGiven_getChangedItem() {
        itemRepository.insert(one());
        ItemDto NewDto = ItemDto.builder()
                .name("New")
                .build();
        Item actual = putToChangeName(one().getId(), NewDto);
        Item expect = Item.builder()
                .id(one().getId())
                .name("New")
                .build();
        Assertions.assertEquals(expect, actual);
    }

    @Test
    void putItemTest_whenNewNameForOtherItemIsGiven_getChangedItem() {
        itemRepository.insert(two());
        ItemDto NewDto = ItemDto.builder()
                .name("Also?")
                .build();
        Item actual = putToChangeName(one().getId(), NewDto);
        Item expect = Item.builder()
                .id(one().getId())
                .name("Also?")
                .build();
        Assertions.assertEquals(expect, actual);
    }

    private Item putToChangeName(String id, ItemDto NewDto) {
        return webTestClient.put()
                .uri("api/item/" + id)
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue(NewDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Item.class)
                .returnResult()
                .getResponseBody();
    }

    private List<Item> getAllItems(String jwt) {
        List<Item> actual = webTestClient.get()
                .uri("/api/item/all")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Item.class)
                .returnResult()
                .getResponseBody();
        return actual;
    }

    private Item createNewItem(String jwt, ItemDto itemDto) {
        Item actual = webTestClient.post()
                .uri("/api/item")
                .headers(http -> http.setBearerAuth(jwt))
                .bodyValue(itemDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Item.class)
                .returnResult()
                .getResponseBody();
        return actual;
    }
}
