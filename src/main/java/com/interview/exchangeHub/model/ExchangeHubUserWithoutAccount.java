package com.interview.exchangeHub.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ExchangeHubUserWithoutAccount implements Serializable {

    private String pesel;
    private String name;
    private String surname;

    public ExchangeHubUserWithoutAccount() {
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
