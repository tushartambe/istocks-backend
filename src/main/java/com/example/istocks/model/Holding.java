package com.example.istocks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "holdings")
public class Holding {
    @Id
    private String id;
    private String email;
    private String symbol;
    private String name;
    private int totalQuantity;
    private BigDecimal averagePrice;
    private BigDecimal totalInvestedAmount;
}