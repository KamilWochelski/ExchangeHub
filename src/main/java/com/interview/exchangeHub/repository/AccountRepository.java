package com.interview.exchangeHub.repository;

import com.interview.exchangeHub.model.CurrencyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<CurrencyAccount, Long> {
}
