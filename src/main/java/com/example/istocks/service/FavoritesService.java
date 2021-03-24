package com.example.istocks.service;

import com.example.istocks.dto.FavoriteStockDto;
import com.example.istocks.dto.StockDto;
import com.example.istocks.model.FavoriteStock;
import com.example.istocks.repository.FavoritesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {
    @Autowired
    private NseService nseService;

    @Autowired
    private FavoritesRepository favoritesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<StockDto> getFavorites(String email) {
        return favoritesRepository.findByEmail(email)
            .stream()
            .map(stock -> nseService.getQuote(stock.getSymbol())).collect(Collectors.toList());
    }

    public FavoriteStock addToFavorites(FavoriteStockDto favoriteStockDto, String email) {
        FavoriteStock favoriteStock = FavoriteStock.builder()
            .email(email)
            .symbol(favoriteStockDto.getSymbol())
            .name(favoriteStockDto.getName())
            .build();

        return favoritesRepository.save(favoriteStock);
    }

    public void removeFromFavorites(FavoriteStockDto favoriteStockDto, String email) {
        List<FavoriteStock> byEmailAndSymbol = favoritesRepository.findByEmailAndSymbol(email, favoriteStockDto.getSymbol());
        favoritesRepository.deleteAll(byEmailAndSymbol);
    }

    public boolean isFavorite(String symbol, String email) {
        List<FavoriteStock> byEmailAndSymbol = favoritesRepository.findByEmailAndSymbol(email, symbol);
        return !byEmailAndSymbol.isEmpty();
    }
}
