package com.example.istocks.controller;

import com.example.istocks.dto.HoldingDto;
import com.example.istocks.service.HoldingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/holdings")
public class HoldingsController {

    @Autowired
    private HoldingsService holdingsService;

    @GetMapping
    public List<HoldingDto> getHoldings(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return holdingsService.getHoldings(userDetails.getUsername());
    }
}
