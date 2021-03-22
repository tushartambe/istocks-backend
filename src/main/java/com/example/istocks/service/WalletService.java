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

    public Wallet initiateWalletWithAmount(BigDecimal initialAmount, String email) {
        Wallet wallet = new Wallet();
        wallet.setEmail(email);
        wallet.setBalance(initialAmount);

        return walletsRepository.save(wallet);
    }

    public WalletBalanceDto getBalance(String email) {
        Wallet wallet = walletsRepository.findByEmail(email);

        WalletBalanceDto walletBalanceDto = new WalletBalanceDto();
        walletBalanceDto.setBalance(wallet.getBalance());
        return walletBalanceDto;
    }

}
