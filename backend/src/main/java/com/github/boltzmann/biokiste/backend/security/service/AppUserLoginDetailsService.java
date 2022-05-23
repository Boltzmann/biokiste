package com.github.boltzmann.biokiste.backend.security.service;

import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserLoginRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppUserLoginDetailsService implements UserDetailsService {

    private final AppUserLoginRepository appUserLoginRepository;

    public AppUserLoginDetailsService(AppUserLoginRepository appUserLoginRepository) {
        this.appUserLoginRepository = appUserLoginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserLoginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return new User(appUser.getUsername(), appUser.getPassword(), List.of());
    }

    public AppUser getUserByName(String username){
        return appUserLoginRepository.findByUsername(username)
                .orElseThrow( () -> new NoSuchElementException("User not found with name: " + username) );
    }

    public String getUserIdByName(String name) {
        return getUserByName(name).getId();
    }

    public String getUsernameById(String id) {
        return appUserLoginRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id) )
                .getUsername();
    }
}
