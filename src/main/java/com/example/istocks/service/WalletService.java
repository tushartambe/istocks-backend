package com.example.istocks.service;

import com.example.istocks.dto.WalletBalanceDto;
import com.example.istocks.model.Wallet;
import com.example.istocks.repository.WalletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {

    @Autowired
    private WalletsRepository walletsRepository;

    public Wallet createWalletForUser(String email) {
        Wallet wallet = Wallet.builder()
            .email(email)
            .balance(BigDecimal.ZERO)
            .build();

        return walletsRepository.save(wallet);
    }

    public Wallet debitAmount(BigDecimal amount, String email) {
        Wallet wallet = walletsRepository.findByEmail(email);
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletsRepository.save(wallet);
    }

    public Wallet creditAmount(BigDecimal amount, String email) {
        Wallet wallet = walletsRepository.findByEmail(email);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletsRepository.save(wallet);
    }

    public WalletBalanceDto getBalance(String email) {
        Wallet wallet = walletsRepository.findByEmail(email);

        return WalletBalanceDto.builder()
            .balance(wallet.getBalance())
            .build();
    }

}
