package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AppUserDetailsServiceTest {
    private final AppUserDetailsRepo appUserDetailsRepo = mock(AppUserDetailsRepo.class);
    private final AppUserDetailsService appUserDetailsService = new AppUserDetailsService(appUserDetailsRepo);

    @Test
    void getListOfSubscriptions(){
        // Given
        List<Item> list = List.of(Item.builder().id("1").name("Banana").build());
        OrganicBox box = OrganicBox.builder()
                .id("1")
                .name("Rohkostkiste")
                .content(list)
                .size("small")
                .weekOfYear(22).build();
        AppUserDetails userDetails = AppUserDetails.builder()
                .id("1")
                .username("Test User")
                .customerId("42")
                .subscribedBoxes(List.of(box)).build();
        when(appUserDetailsRepo.findById("1")).thenReturn(Optional.ofNullable(userDetails));
        // When
        List<OrganicBox> actual = appUserDetailsService.getSubscribedBoxes("1");
        // Then
        Assertions.assertEquals(List.of(box), actual);
    }

}
