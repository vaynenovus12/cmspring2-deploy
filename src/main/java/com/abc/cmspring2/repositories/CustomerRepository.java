package com.abc.cmspring2.repositories;

import com.abc.cmspring2.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Updated to match the "name" field in the new Entity
    List<Customer> findByNameContainingIgnoreCase(String keyword);
    
    // Optional: if you still need email lookups
    List<Customer> findByEmailContainingIgnoreCase(String email);
}