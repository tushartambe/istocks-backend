package com.example.istocks.service;

import com.example.istocks.dto.FavoriteStockDto;
import com.example.istocks.dto.StockDto;
import com.example.istocks.model.FavoriteStock;
import com.example.istocks.repository.FavoritesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

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
            .map(stock -> {
                StockDto quote = null;
                String symbol = null;
                try {
                    symbol = URLEncoder.encode(stock.getSymbol(), UTF_8.name());
                    quote = nseService.getQuote(symbol);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return quote;
            }).collect(Collectors.toList());
    }

    public FavoriteStock addToFavorites(FavoriteStockDto favoriteStockDto, String email) throws UnsupportedEncodingException {
        String symbol = URLDecoder.decode(favoriteStockDto.getSymbol(), UTF_8.name());
        FavoriteStock favoriteStock = FavoriteStock.builder()
            .email(email)
            .symbol(symbol)
            .name(favoriteStockDto.getName())
            .build();

        return favoritesRepository.save(favoriteStock);
    }

    public void removeFromFavorites(FavoriteStockDto favoriteStockDto, String email) throws UnsupportedEncodingException {
        String symbol = URLDecoder.decode(favoriteStockDto.getSymbol(), UTF_8.name());
        List<FavoriteStock> byEmailAndSymbol = favoritesRepository.findByEmailAndSymbol(email, symbol);
        favoritesRepository.deleteAll(byEmailAndSymbol);
    }

    public boolean isFavorite(String symbol, String email) {
        List<FavoriteStock> byEmailAndSymbol = favoritesRepository.findByEmailAndSymbol(email, symbol);
        return !byEmailAndSymbol.isEmpty();
    }
}
