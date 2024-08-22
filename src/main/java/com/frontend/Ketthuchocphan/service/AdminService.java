package com.frontend.Ketthuchocphan.service;


import com.frontend.Ketthuchocphan.Repository.AdminRepository;
import com.frontend.Ketthuchocphan.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Additional methods as needed
}