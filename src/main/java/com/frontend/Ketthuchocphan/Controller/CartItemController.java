package com.frontend.Ketthuchocphan.Controller;

import java.util.Map;
import java.util.List;

import com.frontend.Ketthuchocphan.Repository.CartItemRepository;
import com.frontend.Ketthuchocphan.Repository.CategoryRepository;
import com.frontend.Ketthuchocphan.Repository.CustomerRepository;
import com.frontend.Ketthuchocphan.Repository.ProductRepository;
import com.frontend.Ketthuchocphan.entity.CartItem;
import com.frontend.Ketthuchocphan.entity.Customer;
import com.frontend.Ketthuchocphan.entity.Product;
import com.frontend.Ketthuchocphan.service.CartItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CartItemController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartItemService cartItemService;

    //quản lí giỏ hàng
    @GetMapping("shopping-cart")
    public String shoppingCart(HttpSession session, Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        System.out.println("Customer ID from session: " + customerId);  // Log customerId

        if (customerId != null) {
            Optional<Customer> customer = customerRepository.findById(customerId);

            if (customer.isPresent()) {
                System.out.println("Found customer: " + customer.get().getUsername());  // Log found customer
                model.addAttribute("username", customer.get().getUsername());
                model.addAttribute("customerId", customer.get().getId());

                List<CartItem> cartItems = cartItemService.getCartItemsByCustomerId(customerId);

                model.addAttribute("cartItems", cartItems);
            } else {
                System.out.println("Customer not found");  // Log when customer is not found
            }
        } else {
            System.out.println("Customer ID is null");  // Log when customerId is null
        }
        return "user/shopping-cart";
    }

    @PostMapping("/add-to-cart")
    String create(@ModelAttribute CartItem cartItem, HttpSession session,@RequestParam("product.id") int productId,@RequestParam("quantity") int quantity) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }

        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Check if the product already exists in the customer's cart
            CartItem existingCartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);

            if (existingCartItem != null) {
                // If the product already exists, increase the quantity by quantity
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                cartItemRepository.save(existingCartItem);
            } else {
                // If the product doesn't exist in the cart, create a new CartItem with quantity
                //CartItem cartItem = new CartItem();
                cartItem.setCustomer(customer);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
            }}
        catch (Exception ex) {
            System.out.println("error while add cart item" );
                return "redirect:/shopping-cart"; // Redirect to product list page on error
            }
        return "redirect:/";
    }

    @PostMapping("/shopping-cart/update")
    public String updateCart(@RequestParam Map<String, String> quantities, HttpSession session) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        System.out.println(customerId);
        if (customerId == null) {
            return "redirect:/login";
        }
        for (Map.Entry<String, String> entry : quantities.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();

        if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
            // Loại bỏ tất cả các ký tự không phải số từ key
            String productIdString = key.replaceAll("[^0-9]", "");
            if (!productIdString.isEmpty()) {

                Integer productId = Integer.valueOf(productIdString);
                Integer quantity = Integer.valueOf(value);

                // Kiểm tra giá trị quantity và cập nhật giỏ hàng
                if (quantity > 0) {
                    cartItemService.updateCartItemQuantity(customerId, productId, quantity);
                } else {
                    cartItemService.removeCartItem(customerId, productId);
                }
            }}}
        return "redirect:/shopping-cart";
    }

    @PostMapping("/shopping-cart/remove/{cartItemId}")
    public String removeFromCart(@PathVariable("cartItemId") Integer cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return "redirect:/shopping-cart";
    }

    //---------quản lí giỏ hàng



}