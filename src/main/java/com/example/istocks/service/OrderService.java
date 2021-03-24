package com.example.istocks.service;

import com.example.istocks.constants.OrderType;
import com.example.istocks.constants.TransactionType;
import com.example.istocks.dto.OrderRequestDto;
import com.example.istocks.model.Order;
import com.example.istocks.model.Transaction;
import com.example.istocks.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private OrdersRepository ordersRepository;

    public Order createOrder(OrderRequestDto orderRequestDto, String email) {
        TransactionType transactionType = orderRequestDto.getOrderType().equals(OrderType.BUY) ? TransactionType.DEBIT : TransactionType.CREDIT;

        Transaction transaction = Transaction.builder()
            .email(email)
            .amount(orderRequestDto.getAmount())
            .transactionType(transactionType)
            .description(createDescription(orderRequestDto))
            .date(new Date())
            .build();

        Transaction savedTransaction = transactionService.addTransaction(transaction, email);

        Order order = Order.builder()
            .email(email)
            .transactionId(savedTransaction.getId())
            .amount(savedTransaction.getAmount())
            .date(savedTransaction.getDate())
            .orderType(orderRequestDto.getOrderType())
            .companySymbol(orderRequestDto.getCompanySymbol())
            .companyName(orderRequestDto.getCompanyName())
            .currentSharePrice(orderRequestDto.getCurrentSharePrice())
            .shareQuantity(orderRequestDto.getShareQuantity())
            .build();

        return ordersRepository.save(order);
    }

    private String createDescription(OrderRequestDto orderRequestDto) {
        return String.format("%s %d Shares of %s @ %s", orderRequestDto.getOrderType().name(), orderRequestDto.getShareQuantity(), orderRequestDto.getCompanyName(), orderRequestDto.getCurrentSharePrice());
    }
}
