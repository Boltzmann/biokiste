package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.OrganicBoxRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
class BoxDetailsServiceTest {

    private OrganicBoxRepository organicBoxRepository = mock(OrganicBoxRepository.class);
    private ItemDetailsService itemDetailsService = mock(ItemDetailsService.class);
    private final BoxDetailsService boxDetailsService = new BoxDetailsService(organicBoxRepository, itemDetailsService);

    Item aepfel = Item.builder().id("1").name("Äpfel der Woche").build();
    Item fenchel = Item.builder().id("2").name("Fenchel").build();

    OrganicBox box = OrganicBox.builder()
            .id("1")
            .content(List.of("1","2"))
            .customers(List.of("666"))
            .build();

    @Test
    void whenGetBoxItemsWithValidBoxId_thenReturnTheRightItemList(){
        // Given
        when(organicBoxRepository.existsById("1")).thenReturn(true);
        when(organicBoxRepository.findById("1")).thenReturn(Optional.ofNullable(box));
        List<Item> expected = List.of(aepfel, fenchel);
        when(itemDetailsService.getItemsById(List.of("1","2")))
                .thenReturn(List.of(aepfel, fenchel));
        // When
        List<Item> actual = boxDetailsService.getContentByBoxID("1");
        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getBoxesByUserUnitTest() {
        // Given
        when(organicBoxRepository.findByCustomersIn(List.of("666"))).thenReturn(List.of(box));
        // When
        List<OrganicBox> actual = boxDetailsService.getBoxesByUser("666");
        // Then
        Assertions.assertEquals(List.of(box), actual);
    }
}
