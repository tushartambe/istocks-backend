package com.example.istocks.dto;

import com.example.istocks.constants.OrderType;

import java.math.BigDecimal;

public class OrderRequestDto {
    private BigDecimal amount;
    private OrderType orderType;
    private String companySymbol;
    private String companyName;
    private BigDecimal currentSharePrice;
    private int shareQuantity;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getCompanySymbol() {
        return companySymbol;
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol = companySymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getCurrentSharePrice() {
        return currentSharePrice;
    }

    public void setCurrentSharePrice(BigDecimal currentSharePrice) {
        this.currentSharePrice = currentSharePrice;
    }

    public int getShareQuantity() {
        return shareQuantity;
    }

    public void setShareQuantity(int shareQuantity) {
        this.shareQuantity = shareQuantity;
    }
}