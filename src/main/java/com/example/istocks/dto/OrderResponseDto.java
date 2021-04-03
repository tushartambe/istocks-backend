package com.example.istocks.dto;

import com.example.istocks.constants.OrderType;
import com.example.istocks.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

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
    private Date date;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
            .orderId(order.getId())
            .companyName(order.getCompanyName())
            .companySymbol(order.getCompanySymbol())
            .amount(order.getAmount())
            .currentSharePrice(order.getCurrentSharePrice())
            .orderType(order.getOrderType())
            .shareQuantity(order.getShareQuantity())
            .date(order.getDate())
            .build();
    }
}