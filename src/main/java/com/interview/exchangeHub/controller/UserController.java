package com.interview.exchangeHub.controller;

import com.interview.exchangeHub.exceptions.PeselException;
import com.interview.exchangeHub.model.ExchangeHubUser;
import com.interview.exchangeHub.model.ExchangeHubUserWithoutAccount;
import com.interview.exchangeHub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping()
    public ResponseEntity<Object> postUser(@RequestBody ExchangeHubUserWithoutAccount user) {
        ExchangeHubUser createdUser = null;
        if(user.getName() == null || user.getSurname() == null || user.getPesel()==null) {
            return new ResponseEntity<>("Fields cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(userService.findByPesel(user.getPesel()) != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        try {
            createdUser = userService.registerUser(user);
        } catch (PeselException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CREATED);
        }
        if(createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<List<ExchangeHubUser>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExchangeHubUser> getUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.findByPesel(id), HttpStatus.OK);
    }
}
