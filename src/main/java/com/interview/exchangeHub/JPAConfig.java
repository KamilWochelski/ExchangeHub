package com.interview.exchangeHub;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.interview.exchangeHub")
public class JPAConfig {
}
