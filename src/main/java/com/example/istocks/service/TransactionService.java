package com.example.istocks.service;

import com.example.istocks.constants.TransactionType;
import com.example.istocks.model.Transaction;
import com.example.istocks.model.Wallet;
import com.example.istocks.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private WalletService walletService;


    public Transaction createInitialTransaction(String email) {
        Wallet wallet = walletService.createWalletForUser(email);

        Transaction initialTransaction = new Transaction();
        initialTransaction.setEmail(email);
        initialTransaction.setAmount(BigDecimal.valueOf(20000));
        initialTransaction.setTransactionType(TransactionType.CREDIT);
        initialTransaction.setDescription("Initial amount Credited.");
        initialTransaction.setDate(new Date());
        Transaction savedTransaction = transactionsRepository.save(initialTransaction);

        walletService.creditAmount(savedTransaction.getAmount(), email);

        return savedTransaction;
    }

    public Transaction addTransaction(Transaction transaction, String email) {
        Transaction savedTransaction = transactionsRepository.save(transaction);
        TransactionType transactionType = savedTransaction.getTransactionType();
        if (transactionType.equals(TransactionType.CREDIT)) {
            walletService.debitAmount(savedTransaction.getAmount(), email);
        } else {
            walletService.creditAmount(savedTransaction.getAmount(), email);
        }
        return savedTransaction;
    }

}
