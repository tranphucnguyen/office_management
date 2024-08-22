package com.frontend.Ketthuchocphan.Repository;



import com.frontend.Ketthuchocphan.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}