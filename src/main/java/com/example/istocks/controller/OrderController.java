package com.example.istocks.controller;

import com.example.istocks.dto.ApiError;
import com.example.istocks.dto.OrderRequestDto;
import com.example.istocks.exception.InSufficientBalanceException;
import com.example.istocks.exception.StockNotOwnedException;
import com.example.istocks.model.Order;
import com.example.istocks.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(Authentication authentication, @RequestBody OrderRequestDto orderRequestDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            Order order = orderService.createOrder(userDetails.getUsername(), orderRequestDto);
            return ResponseEntity.ok(order);
        } catch (StockNotOwnedException | InSufficientBalanceException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), "");
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
