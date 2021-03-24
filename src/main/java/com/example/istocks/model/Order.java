package com.example.istocks.model;

import com.example.istocks.constants.OrderType;
import com.example.istocks.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String email;
    private String transactionId;
    private BigDecimal amount;
    private Date date;
    private OrderType orderType;
    private String companySymbol;
    private String companyName;
    private BigDecimal currentSharePrice;
    private int shareQuantity;
}