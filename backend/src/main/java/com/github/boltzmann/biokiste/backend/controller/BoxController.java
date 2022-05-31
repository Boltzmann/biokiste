package com.github.boltzmann.biokiste.backend.controller;

import com.github.boltzmann.biokiste.backend.model.Item;
import com.github.boltzmann.biokiste.backend.security.service.AppUserLoginDetailsService;
import com.github.boltzmann.biokiste.backend.service.BoxDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/box/")
public class BoxController {

    private final AppUserLoginDetailsService appUserLoginDetailsService;
    private final BoxDetailsService boxDetailsService;

    public BoxController(AppUserLoginDetailsService appUserLoginDetailsService, BoxDetailsService boxDetailsService) {
        this.appUserLoginDetailsService = appUserLoginDetailsService;
        this.boxDetailsService = boxDetailsService;
    }

    @GetMapping("{id}")
    public List<Item> getContentBy(@PathVariable String id){
        return boxDetailsService.getContentByBoxID(id);
    }
}
