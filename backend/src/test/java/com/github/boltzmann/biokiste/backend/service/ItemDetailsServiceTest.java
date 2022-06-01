package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class ItemDetailsServiceTest {

    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final ItemDetailsService itemDetailsService = new ItemDetailsService(itemRepository);

    @Test
    void getItemsById() {
        // Given
        Item apple = Item.builder().id("1").name("Banana").build();
        Item raspberry = Item.builder().id("1").name("Raspberry").build();
        Iterable iterable = List.of(apple, raspberry);
        List<String> content = List.of("1", "2");
        when(itemRepository.findById("1")).thenReturn(Optional.of(apple));
        when(itemRepository.findById("2")).thenReturn(Optional.of(raspberry));
        // When
        List<Item> actual = itemDetailsService.getItemsById(content);
        // Then
        Assertions.assertEquals(List.of(apple, raspberry), actual);
    }
}
