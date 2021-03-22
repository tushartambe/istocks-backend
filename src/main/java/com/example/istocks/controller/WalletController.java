package com.example.istocks.controller;

import com.example.istocks.dto.WalletBalanceDto;
import com.example.istocks.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/balance")
    public WalletBalanceDto getBalance(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return walletService.getBalance(userDetails.getUsername());
    }
}
