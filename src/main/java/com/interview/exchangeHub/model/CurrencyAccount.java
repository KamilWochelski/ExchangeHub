package com.interview.exchangeHub.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class CurrencyAccount implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long accId;
    private Double pln;
    private Double usd;

    public CurrencyAccount(Double pln) {
        this.pln = pln;
        usd = new Double(0);
    }

    public CurrencyAccount() {
    }

    public long getAccId() {
        return accId;
    }

    public void setAccId(long id) {
        this.accId = id;
    }

    public double getPln() {
        return pln;
    }

    public void setPln(Double pln) {
        this.pln = pln;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }
}
