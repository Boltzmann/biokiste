package com.github.boltzmann.biokiste.backend.security.controller;

import com.github.boltzmann.biokiste.backend.security.dto.AppUserDto;
import com.github.boltzmann.biokiste.backend.security.service.JWTUtilService;
import com.github.boltzmann.biokiste.backend.security.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AppUserAuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtilService jwtUtilService;
    private final VerificationService verificationService;

    @Autowired
    public AppUserAuthController(AuthenticationManager authenticationManager, JWTUtilService jwtUtilService, VerificationService verificationService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.verificationService = verificationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AppUserDto appUserDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(appUserDto.getUsername(), appUserDto.getPassword())
        );
        String name = appUserDto.getUsername();
        return jwtUtilService.createToken(name);
    }

    @PostMapping("/verify")
    public String verify(@RequestBody AppUserDto appUserDto){
        return "register_success";
    }
}
