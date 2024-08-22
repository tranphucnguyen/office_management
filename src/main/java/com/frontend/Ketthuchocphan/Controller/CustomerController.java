package com.frontend.Ketthuchocphan.Controller;

import com.frontend.Ketthuchocphan.Repository.CategoryRepository;
import com.frontend.Ketthuchocphan.Repository.CustomerRepository;
import com.frontend.Ketthuchocphan.Repository.ProductRepository;
import com.frontend.Ketthuchocphan.entity.Customer;
import com.frontend.Ketthuchocphan.entity.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.frontend.Ketthuchocphan.service.CustomerService;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/")
    public String index(HttpSession session,Model model) {
        // Lấy customerId từ session
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // Tìm kiếm thông tin Customer dựa trên customerId
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                // Thêm username vào model để hiển thị trên view
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());
            }
        }
        try {

            List<Product> productList = productRepository.findByActiveTrue();
            model.addAttribute("productList", productList);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "user/index"; // Trả về trang index.html trong thư mục templates
    }

    @GetMapping("shopping-cart")
    public String shoppingCart(HttpSession session,Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // Tìm kiếm thông tin Customer dựa trên customerId
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                // Thêm username vào model để hiển thị trên view
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());
            }
        }
        return "user/shopping-cart";
    }
    @GetMapping("checkout")
    public String checkout(HttpSession session,Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // Tìm kiếm thông tin Customer dựa trên customerId
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                // Thêm username vào model để hiển thị trên view
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());
            }
        }
        return "user/checkout";
    }
    @GetMapping("login")
    public String login(HttpSession session,Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // Tìm kiếm thông tin Customer dựa trên customerId
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                // Thêm username vào model để hiển thị trên view
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());
            }
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {

        Customer customer = customerRepository.findByEmail(email);

        // Kiểm tra email, mật khẩu và trạng thái kích hoạt (active)
        if (customer == null || !customer.getPassword().equals(password) || !customer.isActive()) {
            model.addAttribute("errorMessage", "Invalid email, password, or account not activated");
            return "user/login";
        }
        // Lưu trữ id của Customer vào session khi đăng nhập thành công
        session.setAttribute("customerId", customer.getId());
        // Logic xử lý sau khi đăng nhập thành công
        return "redirect:/"; // Chuyển hướng đến trang chủ sau khi đăng nhập thành công
    }

    @GetMapping("register")
    public String register(HttpSession session,Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // Tìm kiếm thông tin Customer dựa trên customerId
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                // Thêm username vào model để hiển thị trên view
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());
            }
        }
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "user/register"; // Trả về trang đăng ký với thông báo lỗi
        }

        // Kiểm tra email đã tồn tại hay chưa
        if (customerRepository.existsByEmail(email)) {
            model.addAttribute("errorMessage", "Email is already registered");
            return "user/register"; // Trả về trang đăng ký với thông báo lỗi
        }

        // Tạo mới đối tượng khách hàng và lưu vào database
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(password); // Bạn nên mã hóa mật khẩu trước khi lưu vào database
        customer.setActive(true); // Đặt active thành true (kích hoạt tài khoản)
        customerRepository.save(customer);

        return "redirect:/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
    }

    @GetMapping("shop-details/{id}")
    public String shopDetails(@PathVariable("id") Integer id, Model model,HttpSession session) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId != null) {
            // Tìm kiếm thông tin Customer dựa trên customerId
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                // Thêm username vào model để hiển thị trên view
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());
            }
        }
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
            } else {
                model.addAttribute("errorMessage", "Product not found");
            }
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "user/shop-details";
    }

}