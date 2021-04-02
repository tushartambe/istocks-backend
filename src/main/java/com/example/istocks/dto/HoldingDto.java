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
    private BigDecimal totalInvestedAmount;
    private BigDecimal marketPrice;
    private BigDecimal currentAmount;
    private BigDecimal totalReturns;

    public static HoldingDto from(Holding holding, StockDto quote) {
        BigDecimal marketPrice = new BigDecimal(quote.getLastPrice().replace(",", ""));
        BigDecimal currentAmount = marketPrice.multiply(BigDecimal.valueOf(holding.getTotalQuantity()));
        BigDecimal totalReturns = currentAmount.subtract(holding.getTotalInvestedAmount());

        return HoldingDto.builder()
            .symbol(holding.getSymbol())
            .name(holding.getName())
            .totalQuantity(holding.getTotalQuantity())
            .averagePrice(holding.getAveragePrice())
            .totalInvestedAmount(holding.getTotalInvestedAmount())
            .marketPrice(marketPrice)
            .currentAmount(currentAmount)
            .totalReturns(totalReturns)
            .build();
    }
}