package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.dto.OrganicBoxDto;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
public class GetAllPublicBoxNamesAndIds extends CrudTestWithLogIn {


    @Test
    void whenGetALLOrganicBoxes_thenReturnAllNames(){
        // Given
        organicBoxRepository.insert(fruitBox);
        organicBoxRepository.insert(regioBox);
        // When
        List<OrganicBoxDto> actual = getOrganicBoxDtos();
        // Then
        Assertions.assertEquals(List.of(fruitBoxDto, regioBoxDto), actual);
    }

    @Test
    void whenGetALLOrganicBoxesWithOtherExample_thenReturnAllNames(){
        // Given
        organicBoxRepository.insert(fruitBox);
        organicBoxRepository.insert(newBox);
        // When
        List<OrganicBoxDto> actual = getOrganicBoxDtos();
        // Then
        Assertions.assertEquals(List.of(fruitBoxDto, newBoxDto), actual);
    }

    private List<OrganicBoxDto> getOrganicBoxDtos() {
        List<OrganicBoxDto> actual = webTestClient.get()
                .uri("/allBoxes")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(OrganicBoxDto.class)
                .returnResult()
                .getResponseBody();
        return actual;
    }

    OrganicBoxDto fruitBoxDto = OrganicBoxDto.builder()
            .id("1")
            .name("Fruits")
            .build();
    OrganicBoxDto newBoxDto = OrganicBoxDto.builder()
            .id("3")
            .name("New Box")
            .build();
    OrganicBoxDto regioBoxDto = OrganicBoxDto.builder()
            .id("2")
            .name("Regional")
            .build();
    OrganicBox fruitBox = OrganicBox.builder()
            .id(fruitBoxDto.getId())
            .name(fruitBoxDto.getName())
            .size("big")
            .build();

    OrganicBox newBox = OrganicBox.builder()
            .id(newBoxDto.getId())
            .name(newBoxDto.getName())
            .size("small")
            .build();

    OrganicBox regioBox = OrganicBox.builder()
            .id(regioBoxDto.getId())
            .name(regioBoxDto.getName())
            .size("small")
            .build();
}
