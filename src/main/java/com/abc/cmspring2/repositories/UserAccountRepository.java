package com.abc.cmspring2.repositories;

import com.abc.cmspring2.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> 
{
    Optional<UserAccount> findByUsername(String username);
    UserAccount findByEmployeeEmployeeId(Integer employeeId);
    boolean existsByUsername(String username);
    boolean existsByEmployee_EmployeeId(Integer employeeId);
    boolean existsByCustomer_CustomerId(Integer customerId);   
}

