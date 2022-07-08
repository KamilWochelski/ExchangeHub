package com.interview.exchangeHub.model;

public enum ExchangeRate {
    USD_TO_PLN(4.42),
    PLN_TO_USD(1/USD_TO_PLN.exchangeRate);
    private Double exchangeRate;

    public Double getExchangeRate() {
        return exchangeRate;
    }

    ExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

}
