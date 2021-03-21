package com.example.istocks.dto;

import com.example.istocks.model.FavoriteStock;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StockDto {
    private String symbol;
    private String name;
    private String lastPrice;
    private String open;
    private String previousClose;
    private String dayLow;
    private String dayHigh;
    private String low52;
    private String high52;
    private Map<String, Object> additionalDetails;

    public static StockDto from(FavoriteStock favoriteStock, Map<String, Object> details) {
        StockDto favoriteStockDto = new StockDto();
        favoriteStockDto.setName(favoriteStock.getName());
        favoriteStockDto.setSymbol(favoriteStock.getSymbol());
        favoriteStockDto.setLastPrice(details.get("lastPrice").toString());
        favoriteStockDto.setOpen(details.get("open").toString());
        favoriteStockDto.setPreviousClose(details.get("previousClose").toString());
        favoriteStockDto.setDayLow(details.get("dayLow").toString());
        favoriteStockDto.setDayHigh(details.get("dayHigh").toString());
        favoriteStockDto.setLow52(details.get("low52").toString());
        favoriteStockDto.setHigh52(details.get("high52").toString());
//        favoriteStockDto.setAdditionalDetails(details);
        return favoriteStockDto;
    }
}
