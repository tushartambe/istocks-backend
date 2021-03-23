package com.example.istocks.controller;

import com.example.istocks.dto.OrderRequestDto;
import com.example.istocks.dto.WalletBalanceDto;
import com.example.istocks.model.Order;
import com.example.istocks.service.OrderService;
import com.example.istocks.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order create(Authentication authentication, @RequestBody OrderRequestDto orderRequestDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return orderService.createOrder(orderRequestDto, userDetails.getUsername());
    }
}
