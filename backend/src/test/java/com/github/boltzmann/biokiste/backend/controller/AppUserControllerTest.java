package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.AppUserDetails;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppUserControllerTest extends CrudTestWithLogIn {


    @Test
    void login_whenValidCredentials_thenReturnValidJWTAndGetUserDetails(){
        // Given
        createTestUserInLoginRepoAndGet();
        String jwt = getTokenForTestuser();
        Assertions.assertNotNull(jwt);
        // When
        webTestClient.get()
                .uri("/api/user/me")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserDetails.class)
                .returnResult()
                .getResponseBody();
        AppUserDetails userToChange = new AppUserDetails();
        try{
             AppUser tmpUser = appUserLoginRepository.findByUsername("testuser")
                     .orElseThrow();
             userToChange = appUserDetailsRepo.findById(tmpUser.getId()).orElseThrow();
        } catch (Exception ex){
            Assertions.fail();
        }
        userToChange.setCustomerId("1");
        appUserDetailsRepo.save(userToChange);
        AppUserDetails actual = webTestClient.get()
                .uri("/api/user/me")
                .headers(http -> http.setBearerAuth(jwt))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AppUserDetails.class)
                .returnResult()
                .getResponseBody();
        // Then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("testuser", actual.getUsername());
        Assertions.assertEquals("1", actual.getCustomerId());
    }

    private String getTokenForTestuser() {
        return getTokenFor("testuser", "passwort");
    }

    private AppUser createTestUserInLoginRepoAndGet(){
        return createTestUserInLoginRepoAndGet("1","testuser", "passwort");
    }

}
