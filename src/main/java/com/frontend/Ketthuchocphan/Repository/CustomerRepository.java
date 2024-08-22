package com.frontend.Ketthuchocphan.Repository;

import com.frontend.Ketthuchocphan.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);
    Customer findByEmail(String email);
    boolean existsByEmail(String email);
}
