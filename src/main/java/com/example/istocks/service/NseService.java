package com.example.istocks.service;

import com.example.istocks.dto.StockDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class NseService {

    private final RestTemplate restTemplate;

    @Autowired
    private final ObjectMapper objectMapper;

    public NseService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
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

    public StockDto getQuote(String companySymbol) throws UnsupportedEncodingException {
        String url = "http://localhost:3000/nse/get_quote_info?companyName=" + companySymbol;
        Map quote = restTemplate.getForObject(url, Map.class);
        Map<String, Object> data = (Map<String, Object>) ((ArrayList<Object>) quote.get("data")).get(0);
        String decode = URLDecoder.decode(companySymbol, UTF_8.name());
        data.put("symbol", decode);
        return StockDto.from(data);
    }

    public JsonNode getIndexStocks(String indexName) {
        String url = " http://localhost:3000/nse/get_index_stocks?symbol=" + indexName;
        return restTemplate.getForObject(url, JsonNode.class);
    }

}
