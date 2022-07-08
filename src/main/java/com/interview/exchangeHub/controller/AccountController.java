package com.interview.exchangeHub.controller;

import com.interview.exchangeHub.controller.model.AccountInput;
import com.interview.exchangeHub.exceptions.NotEnoughFundsException;
import com.interview.exchangeHub.model.CurrencyAccount;
import com.interview.exchangeHub.model.ExchangeHubUser;
import com.interview.exchangeHub.repository.AccountRepository;
import com.interview.exchangeHub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;
    @Autowired
    AccountRepository accountRepository;
    @PostMapping(
            value = "/exchangeUsdToPln")
    public ResponseEntity<Object> postExchangeUsdToPln(@RequestBody AccountInput accountInput) {
        CurrencyAccount account;
        try {
            account = userService.exchangeUsdToPln(accountInput.money, accountInput.pesel);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(account, HttpStatus.OK);
    }

    @PostMapping(
            value = "/exchangePlnToUsd")
    public ResponseEntity<Object> postExchangePlnToUsd(@RequestBody AccountInput accountInput) {
        CurrencyAccount ac;
        try {
            ac = userService.exchangePlnToUsd(accountInput.money, accountInput.pesel);
        } catch (NotEnoughFundsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(ac, HttpStatus.OK);
    }

    @PostMapping(
            value = "/sendPln")
    public ResponseEntity<Object> postSendPln(@RequestBody AccountInput accountInput) {
        try {
            ExchangeHubUser user = userService.findByPesel(accountInput.pesel);
            if(user == null) {
                return new ResponseEntity("No user with given pesel", HttpStatus.BAD_REQUEST);
            }
            CurrencyAccount account = user.getAccount();
            account.setPln(account.getPln()+accountInput.money);
            accountRepository.save(account);
        } catch (NotEnoughFundsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(
            value = "/sendUsd")
    public ResponseEntity<Object> postSendUsd(@RequestBody AccountInput accountInput) {
        try {
            ExchangeHubUser user = userService.findByPesel(accountInput.pesel);
            if(user == null) {
                return new ResponseEntity("No user with given pesel", HttpStatus.BAD_REQUEST);
            }
            CurrencyAccount account = user.getAccount();
            account.setPln(account.getUsd()+accountInput.money);
            accountRepository.save(account);
        } catch (NotEnoughFundsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping(
            value = "{pesel}")
    public ResponseEntity<Object> getAccount(@PathVariable String pesel) {
        ExchangeHubUser user = userService.findByPesel(pesel);
        if(user == null) {
            return new ResponseEntity<>("No user with given pesel", HttpStatus.BAD_REQUEST);
        }
        CurrencyAccount account = user.getAccount();
        return new ResponseEntity(account, HttpStatus.OK);
    }



}
