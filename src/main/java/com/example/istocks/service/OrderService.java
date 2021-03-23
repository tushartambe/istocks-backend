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

        Transaction transaction = new Transaction();
        transaction.setEmail(email);
        transaction.setAmount(orderRequestDto.getAmount());
        transaction.setTransactionType(transactionType);
        transaction.setDescription(createDescription(orderRequestDto));
        transaction.setDate(new Date());

        Transaction savedTransaction = transactionService.addTransaction(transaction, email);

        Order order = new Order();
        order.setEmail(email);
        order.setTransactionId(savedTransaction.getId());
        order.setAmount(savedTransaction.getAmount());
        order.setDate(savedTransaction.getDate());
        order.setOrderType(orderRequestDto.getOrderType());
        order.setCompanySymbol(orderRequestDto.getCompanySymbol());
        order.setCompanyName(orderRequestDto.getCompanyName());
        order.setCurrentSharePrice(orderRequestDto.getCurrentSharePrice());
        order.setShareQuantity(orderRequestDto.getShareQuantity());

        return ordersRepository.save(order);
    }

    private String createDescription(OrderRequestDto orderRequestDto) {
        return String.format("%s %d Shares of %s @ %s", orderRequestDto.getOrderType().name(), orderRequestDto.getShareQuantity(), orderRequestDto.getCompanyName(), orderRequestDto.getCurrentSharePrice());
    }
}
