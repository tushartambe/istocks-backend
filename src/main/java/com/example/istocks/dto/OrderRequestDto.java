package com.example.istocks.dto;

import com.example.istocks.constants.OrderType;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private BigDecimal amount;
    private OrderType orderType;
    private String companySymbol;
    private String companyName;
    private BigDecimal currentSharePrice;
    private int shareQuantity;
}