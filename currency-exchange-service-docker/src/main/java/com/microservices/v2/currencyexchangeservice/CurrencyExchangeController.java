package com.microservices.v2.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;


    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
                                                  @PathVariable String to){
        //CurrencyExchange currencyExchange = new CurrencyExchange(100L,from, to, BigDecimal.valueOf(50));
        //INFO [currency-exchange,65366219b11d72fbbe0ab2df639b0099,69138d96d10d3aa7] 21948 --- [nio-8000-exec-1]
        //c.m.v.c.CurrencyExchangeController       : retrieveExchangeValue  called AUD to INR
        logger.info("retrieveExchangeValue  called {} to {}", from ,to);
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(currencyExchange == null){
            throw new RuntimeException(("Unable to find data for "+from + " to " + to));
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
