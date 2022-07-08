package com.interview.exchangeHub.repository;

import com.interview.exchangeHub.model.ExchangeHubUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ExchangeHubUser, String> {
    public ExchangeHubUser findByPesel(String pesel);
}
