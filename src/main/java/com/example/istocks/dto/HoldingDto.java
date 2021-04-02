package com.example.istocks.dto;

import com.example.istocks.model.Holding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldingDto {
    private String symbol;
    private String name;
    private int totalQuantity;
    private BigDecimal averagePrice;
    private BigDecimal totalPrice;

    public static HoldingDto from(Holding holding) {
        return HoldingDto.builder()
            .symbol(holding.getSymbol())
            .name(holding.getName())
            .totalQuantity(holding.getTotalQuantity())
            .averagePrice(holding.getAveragePrice())
            .totalPrice(holding.getTotalPrice())
            .build();
    }
}