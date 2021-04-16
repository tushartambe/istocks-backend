package com.example.istocks.service;

import com.example.istocks.dto.HoldingDto;
import com.example.istocks.dto.StockDto;
import com.example.istocks.exception.StockNotOwnedException;
import com.example.istocks.model.Holding;
import com.example.istocks.model.Order;
import com.example.istocks.repository.HoldingRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.istocks.constants.ExceptionMessages.STOCK_NOT_OWNED;
import static com.example.istocks.constants.OrderType.BUY;

@Service
public class HoldingsService {
    @Autowired
    private HoldingRepository holdingsRepository;

    @Autowired
    private NseService nseService;

    public List<HoldingDto> getHoldings(String email) {
        List<Holding> holdings = holdingsRepository.findByEmail(email);
        return holdings.stream()
            .filter(h -> h.getTotalQuantity() > 0) //TODO : Logic for deleting holding if shares become 0
            .map(holding -> {
                StockDto quote = null;
                try {
                    quote = nseService.getQuote(holding.getSymbol());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return HoldingDto.from(holding, quote);
            })
            .collect(Collectors.toList());
    }

    public HoldingDto getHoldingFor(String email, String symbol) throws StockNotOwnedException, UnsupportedEncodingException {
        Holding holding = getHolding(email, symbol);
        return getHoldingDto(holding);
    }

    public Holding updateHoldings(String email, Order order) {
        if (order.getOrderType().equals(BUY)) {
            return updateHoldingsForBuyOrder(email, order);
        } else {
            return updateHoldingsForSellOrder(email, order);
        }
    }

    public Holding getHolding(String email, String symbol) {
        return holdingsRepository.findByEmailAndSymbol(email, symbol);
    }

    private HoldingDto getHoldingDto(Holding holding) throws StockNotOwnedException, UnsupportedEncodingException {
        if (holding.getTotalQuantity() < 0) {
            throw new StockNotOwnedException(STOCK_NOT_OWNED);
        }

        StockDto quote = nseService.getQuote(holding.getSymbol());
        return HoldingDto.from(holding, quote);
    }

    private Holding updateHoldingsForBuyOrder(String email, Order order) {
        Holding holding = holdingsRepository.findByEmailAndSymbol(email, order.getCompanySymbol());

        if (Objects.isNull(holding)) {
            return addNewHolding(email, order);
        } else {
            int newQuantity = holding.getTotalQuantity() + order.getShareQuantity();
            BigDecimal updatedAmount = holding.getTotalInvestedAmount().add(order.getAmount());
            BigDecimal averagePrice = updatedAmount.divide(BigDecimal.valueOf(newQuantity));

            holding.setTotalQuantity(newQuantity);
            holding.setTotalInvestedAmount(updatedAmount);
            holding.setAveragePrice(averagePrice);
            return holdingsRepository.save(holding);
        }
    }

    private Holding updateHoldingsForSellOrder(String email, Order order) {
        Holding holding = holdingsRepository.findByEmailAndSymbol(email, order.getCompanySymbol());

        int newQuantity = holding.getTotalQuantity() - order.getShareQuantity();
        BigDecimal updatedAmount = holding.getAveragePrice().multiply(BigDecimal.valueOf(newQuantity));

        holding.setTotalQuantity(newQuantity);
        holding.setTotalInvestedAmount(updatedAmount);

        return holdingsRepository.save(holding);
    }

    private Holding addNewHolding(String email, Order order) {
        Holding holding = Holding.builder()
            .email(email)
            .symbol(order.getCompanySymbol())
            .name(order.getCompanyName())
            .averagePrice(order.getCurrentSharePrice())
            .totalQuantity(order.getShareQuantity())
            .totalInvestedAmount(order.getAmount())
            .build();

        return holdingsRepository.save(holding);
    }
}
