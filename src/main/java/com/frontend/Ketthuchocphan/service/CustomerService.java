package com.frontend.Ketthuchocphan.service;

import com.frontend.Ketthuchocphan.Repository.CustomerRepository;
import com.frontend.Ketthuchocphan.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean registerCustomer(String username, String password, String email) {
        if (customerRepository.findByUsername(username) != null || customerRepository.findByEmail(email) != null) {
            return false; // Username or email already exists
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password); // Ideally, you should hash the password before saving
        customer.setEmail(email);
        customer.setActive(true); // Set default active status

        customerRepository.save(customer);
        return true;
    }
}

