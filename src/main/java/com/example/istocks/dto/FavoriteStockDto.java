package com.example.istocks.dto;

import com.example.istocks.model.FavoriteStock;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FavoriteStockDto {
    private String symbol;
    private String name;
}