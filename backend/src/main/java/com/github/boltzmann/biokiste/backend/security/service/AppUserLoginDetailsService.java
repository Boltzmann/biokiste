package com.github.boltzmann.biokiste.backend.security.service;

import com.github.boltzmann.biokiste.backend.dto.AppUserDetails;
import com.github.boltzmann.biokiste.backend.security.model.AppUser;
import com.github.boltzmann.biokiste.backend.security.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppUserLoginDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserLoginDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return new User(appUser.getUsername(), appUser.getPassword(), List.of());
    }

    public AppUser getUserByName(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow( () -> new NoSuchElementException("User not found with name: " + username) );
    }


}
