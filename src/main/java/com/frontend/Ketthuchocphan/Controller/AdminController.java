package com.frontend.Ketthuchocphan.Controller;


import com.frontend.Ketthuchocphan.entity.Admin;


import com.frontend.Ketthuchocphan.service.AdminService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Admin admin = adminService.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password) && admin.getEnabled()) {
            session.setAttribute("loggedInAdmin", admin);
            return "redirect:/admin/index";
        }
        model.addAttribute("error", "Invalid credentials");
        return "admin/login";
    }

    @GetMapping("/admin/index")
    public String dashboard(HttpSession session) {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        Admin loggedInAdmin = (Admin) session.getAttribute("loggedInAdmin");
        if (loggedInAdmin == null) {
            return "redirect:/admin";
        }
        return "admin/index";
    }
}
