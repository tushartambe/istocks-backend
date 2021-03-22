package com.example.istocks.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletBalanceDto {
    private BigDecimal balance;
}
