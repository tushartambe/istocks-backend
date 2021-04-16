package com.example.istocks.controller;

import com.example.istocks.dto.ApiError;
import com.example.istocks.dto.HoldingDto;
import com.example.istocks.exception.StockNotOwnedException;
import com.example.istocks.service.HoldingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
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

    @GetMapping("/single")
    public ResponseEntity<?> getHoldingDetails(Authentication authentication, @RequestParam String symbol) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            HoldingDto holding = holdingsService.getHoldingFor(userDetails.getUsername(), symbol);
            return ResponseEntity.ok(holding);
        } catch (StockNotOwnedException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "");
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        } catch (UnsupportedEncodingException e) {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), "");
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
