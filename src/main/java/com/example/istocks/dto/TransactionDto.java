package com.example.istocks.dto;

import com.example.istocks.constants.TransactionType;
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
public class TransactionDto {
    private String transactionId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private Date date;
    private String description;
    private OrderResponseDto orderResponse;
}
