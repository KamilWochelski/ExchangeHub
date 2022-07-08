package com.interview.exchangeHub.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
public class ExchangeHubUser implements Serializable {

    @Id
    @Pattern(regexp = "^\\d{11}$", message = "Invalid PESEL format. Must contain 11 digit number")
    private String pesel;
    private String name;
    private String surname;
    @OneToOne
    private CurrencyAccount account;

    public ExchangeHubUser() {
    }

    public ExchangeHubUser(@Pattern(regexp = "^\\d{11}$", message = "Invalid PESEL format. Must contain 11 digit number") String pesel, String name, String surname, CurrencyAccount account) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.account = account;
    }


    public CurrencyAccount getAccount() {
        return account;
    }

    public void setAccount(CurrencyAccount account) {
        this.account = account;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
