package com.example.istocks.service;

import com.example.istocks.constants.TransactionType;
import com.example.istocks.dto.OrderResponseDto;
import com.example.istocks.dto.TransactionDto;
import com.example.istocks.model.Order;
import com.example.istocks.model.Transaction;
import com.example.istocks.model.Wallet;
import com.example.istocks.repository.OrdersRepository;
import com.example.istocks.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private WalletService walletService;

    public List<TransactionDto> getTransactions(String email) {
        List<Transaction> transactions = transactionsRepository.findByEmail(email);
        return transactions.stream()
            .sorted((t1, t2) -> Long.compare(t2.getDate().getTime(), t1.getDate().getTime()))
            .map(transaction -> {
                TransactionDto transactionDto = TransactionDto.builder()
                    .transactionId(transaction.getId())
                    .date(transaction.getDate())
                    .transactionType(transaction.getTransactionType())
                    .description(transaction.getDescription())
                    .amount(transaction.getAmount())
                    .build();

                Order order = ordersRepository.findByTransactionId(transaction.getId());
                if (!Objects.isNull(order)) {
                    OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                        .orderId(order.getId())
                        .companyName(order.getCompanyName())
                        .companySymbol(order.getCompanySymbol())
                        .amount(order.getAmount())
                        .currentSharePrice(order.getCurrentSharePrice())
                        .orderType(order.getOrderType())
                        .shareQuantity(order.getShareQuantity())
                        .build();
                    transactionDto.setOrderResponse(orderResponseDto);
                }

                return transactionDto;
            }).collect(Collectors.toList());
    }

    public Transaction createInitialTransaction(String email) {
        Wallet wallet = walletService.createWalletForUser(email);
        Transaction initialTransaction = Transaction.builder()
            .email(email)
            .amount(BigDecimal.valueOf(20000))
            .transactionType(TransactionType.CREDIT)
            .description("Initial amount Credited.")
            .date(new Date())
            .build();
        Transaction savedTransaction = transactionsRepository.save(initialTransaction);
        walletService.creditAmount(savedTransaction.getAmount(), email);
        return savedTransaction;
    }

    public Transaction addTransaction(Transaction transaction, String email) {
        Transaction savedTransaction = transactionsRepository.save(transaction);
        TransactionType transactionType = savedTransaction.getTransactionType();
        if (transactionType.equals(TransactionType.CREDIT)) {
            walletService.creditAmount(savedTransaction.getAmount(), email);
        } else {
            walletService.debitAmount(savedTransaction.getAmount(), email);
        }
        return savedTransaction;
    }

}
