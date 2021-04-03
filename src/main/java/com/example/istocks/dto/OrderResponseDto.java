package com.example.istocks.dto;

import com.example.istocks.constants.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private String orderId;
    private String companySymbol;
    private String companyName;
    private BigDecimal amount;
    private BigDecimal currentSharePrice;
    private int shareQuantity;
    private OrderType orderType;
}