package com.example.istocks.controller;

import com.example.istocks.dto.StockDto;
import com.example.istocks.service.NseService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/nse")
public class NseController {

    @Autowired
    private NseService nseService;

    @GetMapping("/get_market_status")
    public JsonNode getMarketStatus() {
        return nseService.getMarketStatus();
    }

    @GetMapping("/get_indices")
    public JsonNode getIndices() {
        return nseService.getIndices();
    }

    @GetMapping("/get_gainers")
    public JsonNode getGainers() {
        return nseService.getGainers();
    }

    @GetMapping("/get_losers")
    public JsonNode getLosers() {
        return nseService.getLosers();
    }

    @GetMapping("/get_quote_info")
    public StockDto getQuote(@RequestParam String companyName) throws UnsupportedEncodingException {
        return nseService.getQuote(companyName);
    }

    @GetMapping("/get_index_stocks")
    public JsonNode getIndexStocks(@RequestParam String symbol) {
        return nseService.getIndexStocks(symbol);
    }
}
