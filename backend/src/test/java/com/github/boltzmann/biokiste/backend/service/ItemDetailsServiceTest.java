package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.dto.ItemDto;
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

    Item apple() {
        return Item.builder().id("1").name("apple").build();
    };

    Item raspberry() {
        return Item.builder().id("2").name("Raspberry").build();
    };

    @Test
    void getItemsById() {
        // Given
        List<String> content = List.of("1", "2");
        when(itemRepository.findById("1")).thenReturn(Optional.of(apple()));
        when(itemRepository.findById("2")).thenReturn(Optional.of(raspberry()));
        // When
        List<Item> actual = itemDetailsService.getItemsById(content);
        // Then
        Assertions.assertEquals(List.of(apple(), raspberry()), actual);
    }

    @Test
    void getAllItems_whenTwoItemsInRepo_thenGetListOfTwoItemsFromRepo() {
        when(itemRepository.findAll()).thenReturn(List.of(apple(), raspberry()));
        List<Item> actual = itemDetailsService.getAllItems();
        Assertions.assertEquals(List.of(apple(), raspberry()), actual);
    }

    @Test
    void getAllItems_whenNoItemInRepo_thenGetEmptyList() {
        when(itemRepository.findAll()).thenReturn(List.of());
        List<Item> actual = itemDetailsService.getAllItems();
        Assertions.assertEquals(List.of(), actual);
    }

    @Test
    void addNewItem() {
        when(itemRepository.insert(Item.builder().name("apple").build())).thenReturn(apple());
        Item actual = itemDetailsService.addNewItem(
                ItemDto.builder().name(apple().getName()).build()
        );
        Assertions.assertEquals(apple(), actual);
    }

    @Test
    void addNewItem_whenItemAlreadyInRepo_thenIllegalArgumentException() {
        when(itemRepository.findItemByName("apple")).thenReturn(null).thenReturn(apple());

        Item actual = itemDetailsService.addNewItem(
                ItemDto.builder().name(apple().getName()).build()
        );
        ItemDto dto = ItemDto.builder().name(apple().getName()).build();
        Exception exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> itemDetailsService.addNewItem(dto)
        );
    }

    @Test
    void changeItem() {
        itemRepository.insert(apple());
        Item raspberry2update = apple();
        raspberry2update.setId(apple().getId());
        when(itemRepository.save(raspberry2update))
                .thenReturn(Item.builder()
                        .id(apple().getId())
                        .name(raspberry().getName())
                        .build());
        Item actual = itemDetailsService.changeItem(raspberry2update);
        Item expected = Item.builder().id(apple().getId()).name(raspberry().getName()).build();
        Assertions.assertEquals(expected, actual);
    }
}
