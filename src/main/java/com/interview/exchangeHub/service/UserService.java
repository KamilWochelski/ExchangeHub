package com.interview.exchangeHub.service;

import com.interview.exchangeHub.exceptions.NotEnoughFundsException;
import com.interview.exchangeHub.exceptions.PeselException;
import com.interview.exchangeHub.model.CurrencyAccount;
import com.interview.exchangeHub.model.ExchangeHubUserWithoutAccount;
import com.interview.exchangeHub.model.ExchangeRate;
import com.interview.exchangeHub.model.ExchangeHubUser;
import com.interview.exchangeHub.repository.AccountRepository;
import com.interview.exchangeHub.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    public ExchangeHubUser registerUser(ExchangeHubUserWithoutAccount user) {
        if(!isValidPesel(user.getPesel())) {
            throw new PeselException();
        }

        CurrencyAccount ac = accountRepository.save(new CurrencyAccount(100.00));
        ExchangeHubUser createdUser = userRepository.save(new ExchangeHubUser(user.getPesel(), user.getName(), user.getSurname(), ac));
        return createdUser;
    }

    public CurrencyAccount exchangeUsdToPln(Double usd, String pesel) {
        ExchangeHubUser user = userRepository.findByPesel(pesel);
        if(user == null)
            throw new RuntimeException("No user with given Pesel");
        CurrencyAccount ac = user.getAccount();
        if(ac.getUsd() < usd) {
            throw new NotEnoughFundsException();
        }
        ac.setUsd(ac.getUsd() - usd);
        ac.setPln(ac.getPln() + Double.valueOf(usd*ExchangeRate.USD_TO_PLN.getExchangeRate()));
        ac = accountRepository.save(ac);
        return ac;

    }

    public CurrencyAccount exchangePlnToUsd(Double pln, String pesel) {
        ExchangeHubUser user = userRepository.findByPesel(pesel);
        if(user == null)
            throw new RuntimeException("No user with given Pesel");
        CurrencyAccount ac = user.getAccount();

        if(ac.getPln() < pln) {
            throw new NotEnoughFundsException();
        }
        ac.setPln(ac.getPln() - pln);
        ac.setUsd(ac.getUsd() + Double.valueOf(pln*ExchangeRate.PLN_TO_USD.getExchangeRate()));
        ac = accountRepository.save(ac);
        return ac;
    }

    public ExchangeHubUser findByPesel(String pesel) {
        return userRepository.findByPesel(pesel);
    }

    public List<ExchangeHubUser> getUsers() {
        return userRepository.findAll();
    }

    private boolean isValidPesel(String pesel) {
        if(pesel.length() > 2 && StringUtils.isNumeric(pesel) && pesel.matches("^\\d{11}$")) {
            String year = pesel.substring(0,2);

            Integer peselYear = Integer.parseInt(year)+2000;
            Integer current = Calendar.getInstance().get(Calendar.YEAR);
            //Can be that pesels will overlap at certain point and to find out age from
            //2 first numbers will get slighly more challanging (After 2050 for this algorithm
            if(Double.parseDouble(year)>50 || current-peselYear > 17) {
                return true;
            }
        }
        return false;
    }
}
