package com.example.istocks.controller;

import com.example.istocks.dto.FavoriteStockDto;
import com.example.istocks.dto.StockDto;
import com.example.istocks.model.FavoriteStock;
import com.example.istocks.repository.FavoritesRepository;
import com.example.istocks.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class favoritesController {
    @Autowired
    private FavoritesRepository favoritesRepository;

    @Autowired
    private FavoritesService favoritesService;

    @GetMapping
    public List<StockDto> getAllFavorites(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return favoritesService.getFavorites(userDetails.getUsername());
    }

    @PostMapping("/add")
    public FavoriteStock addToFavorite(Authentication authentication, @RequestBody FavoriteStockDto favoriteStockDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return favoritesService.addToFavorite(favoriteStockDto, userDetails.getUsername());
    }
}
