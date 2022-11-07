package com.moneywareservice.repository;


import com.moneywareservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
  Optional<Customer> findByUsername(String username);
}
