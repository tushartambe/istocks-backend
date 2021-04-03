package com.example.istocks.controller;

import com.example.istocks.dto.ApiError;
import com.example.istocks.dto.OrderRequestDto;
import com.example.istocks.dto.OrderResponseDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrders(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        try {
            List<OrderResponseDto> orders = orderService.getOrders(userDetails.getUsername());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), "");
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

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
