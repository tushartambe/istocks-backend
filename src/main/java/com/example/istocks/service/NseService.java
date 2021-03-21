package com.example.istocks.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NseService {

    private final RestTemplate restTemplate;

    public NseService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public JsonNode getMarketStatus() {
        String url = "http://localhost:3000/get_market_status";
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getIndices() {
        String url = "http://localhost:3000/nse/get_indices";
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getGainers() {
        String url = "http://localhost:3000/nse/get_gainers";
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getLosers() {
        String url = "http://localhost:3000/nse/get_losers";
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getQuote(String companySymbol) {
        String url = "http://localhost:3000/nse/get_quote_info?companyName=" + companySymbol;
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getIndexStocks(String indexName) {
        String url = " http://localhost:3000/nse/get_index_stocks?symbol=" + indexName;
        return restTemplate.getForObject(url, JsonNode.class);
    }

}
