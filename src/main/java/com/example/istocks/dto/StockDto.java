package com.example.istocks.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static StockDto from(Map<String, Object> details) {
        return StockDto.builder()
            .name(details.get("companyName").toString())
            .symbol(details.get("symbol").toString())
            .lastPrice(details.get("lastPrice").toString())
            .open(details.get("open").toString())
            .previousClose(details.get("previousClose").toString())
            .dayLow(details.get("dayLow").toString())
            .dayHigh(details.get("dayHigh").toString())
            .low52(details.get("low52").toString())
            .high52(details.get("high52").toString())
//            .additionalDetails(details)
            .build();
    }
}
