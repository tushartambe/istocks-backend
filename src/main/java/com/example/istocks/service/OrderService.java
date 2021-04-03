package com.example.istocks.service;

import com.example.istocks.constants.TransactionType;
import com.example.istocks.dto.OrderRequestDto;
import com.example.istocks.dto.OrderResponseDto;
import com.example.istocks.exception.InSufficientBalanceException;
import com.example.istocks.exception.StockNotOwnedException;
import com.example.istocks.model.Holding;
import com.example.istocks.model.Order;
import com.example.istocks.model.Transaction;
import com.example.istocks.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.istocks.constants.ExceptionMessages.*;
import static com.example.istocks.constants.OrderType.*;

@Service
public class OrderService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private HoldingsService holdingsService;

    public List<OrderResponseDto> getOrders(String email) {
        List<Order> orders = ordersRepository.findByEmail(email);
        return orders.stream()
            .sorted((t1, t2) -> Long.compare(t2.getDate().getTime(), t1.getDate().getTime()))
            .map(OrderResponseDto::from).collect(Collectors.toList());
    }

    public Order createOrder(String email, OrderRequestDto orderRequestDto) throws StockNotOwnedException, InSufficientBalanceException {
        TransactionType transactionType = orderRequestDto.getOrderType().equals(BUY) ? TransactionType.DEBIT : TransactionType.CREDIT;

        if (orderRequestDto.getOrderType().equals(BUY)) {
            return createBuyOrder(email, orderRequestDto);
        } else {
            return createSellOrder(email, orderRequestDto);
        }
    }

    private Order createBuyOrder(String email, OrderRequestDto orderRequestDto) throws InSufficientBalanceException {
        validateBuyOrder(orderRequestDto, email);
        return saveOrderDetails(email, orderRequestDto, TransactionType.DEBIT);
    }

    private Order createSellOrder(String email, OrderRequestDto orderRequestDto) throws StockNotOwnedException {
        validateSellOrder(orderRequestDto, email);
        return saveOrderDetails(email, orderRequestDto, TransactionType.CREDIT);
    }

    private Order saveOrderDetails(String email, OrderRequestDto orderRequestDto, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
            .email(email)
            .amount(orderRequestDto.getAmount())
            .transactionType(transactionType)
            .description(createDescription(orderRequestDto))
            .date(new Date())
            .build();

        Transaction savedTransaction = transactionService.addTransaction(transaction, email);

        Order order = Order.builder()
            .email(email)
            .transactionId(savedTransaction.getId())
            .amount(savedTransaction.getAmount())
            .date(savedTransaction.getDate())
            .orderType(orderRequestDto.getOrderType())
            .companySymbol(orderRequestDto.getCompanySymbol())
            .companyName(orderRequestDto.getCompanyName())
            .currentSharePrice(orderRequestDto.getCurrentSharePrice())
            .shareQuantity(orderRequestDto.getShareQuantity())
            .build();

        holdingsService.updateHoldings(email, order);
        return ordersRepository.save(order);
    }

    private void validateBuyOrder(OrderRequestDto orderRequestDto, String email) throws InSufficientBalanceException {
        BigDecimal balance = walletService.getBalance(email).getBalance();
        if (balance.compareTo(orderRequestDto.getAmount()) < 0) {
            throw new InSufficientBalanceException(IN_SUFFICIENT_BALANCE_FOR_ORDER);
        }
    }

    private void validateSellOrder(OrderRequestDto orderRequestDto, String email) throws StockNotOwnedException {
        Holding holding = holdingsService.getHolding(email, orderRequestDto.getCompanySymbol());

        if (Objects.isNull(holding)) {
            throw new StockNotOwnedException(STOCK_NOT_OWNED);
        }

        if (holding.getTotalQuantity() < orderRequestDto.getShareQuantity()) {
            throw new StockNotOwnedException(NOT_ENOUGH_STOCKS_AVAILABLE);
        }
    }

    private String createDescription(OrderRequestDto orderRequestDto) {
        return String.format("%s %d Shares of %s @ %s", orderRequestDto.getOrderType().name(), orderRequestDto.getShareQuantity(), orderRequestDto.getCompanyName(), orderRequestDto.getCurrentSharePrice());
    }
}
