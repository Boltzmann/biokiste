package com.github.boltzmann.biokiste.backend.service;

import com.github.boltzmann.biokiste.backend.model.OrganicBox;
import com.github.boltzmann.biokiste.backend.repository.AppUserDetailsRepo;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AppUserDetailsServiceTest {

    AppUserDetailsRepo appUserDetailsRepo = mock(AppUserDetailsRepo.class);
    BoxDetailsService boxDetailsService = mock(BoxDetailsService.class);
    EmailService emailService = mock(EmailService.class);
    AppUserLoginRepository appUserLoginRepository = mock(AppUserLoginRepository.class);
    AppUserDetailsService appUserDetailsService =
            new AppUserDetailsService(appUserDetailsRepo, boxDetailsService, emailService, appUserLoginRepository);

    private OrganicBox firstBox() {
        return OrganicBox.builder()
                .id("1")
                .customers(List.of("1","1","2"))
                .build();
    }
    private OrganicBox secondBox() {
        return OrganicBox.builder()
                .id("2")
                .customers(List.of("2"))
                .build(); }

    @Test
    void getSubscriptionsOfOneUser() {
        /**
         * In the customers list of box user with id "1" is mentioned
         * two times. This means, that she has subscribed one box twice.
         * This test checks if this is resolved by the service method.
         */
        // When
        when(boxDetailsService.getBoxesByUser("1"))
                .thenReturn(List.of(firstBox()));
        List<OrganicBox> actual = appUserDetailsService.getSubscriptionsOfUser("1");
        // Then
        List<OrganicBox> expected = List.of(firstBox(), firstBox());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getSubscriptionsOfOtherUser() {
        // When
        when(boxDetailsService.getBoxesByUser("2"))
                .thenReturn(List.of(firstBox(), secondBox()));
        List<OrganicBox> actual = appUserDetailsService.getSubscriptionsOfUser("2");
        // Then
        List<OrganicBox> expected = List.of(firstBox(), secondBox());
        Assertions.assertEquals(expected, actual);
    }
}

