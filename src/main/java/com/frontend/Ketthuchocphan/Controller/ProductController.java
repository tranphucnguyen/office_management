package com.frontend.Ketthuchocphan.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frontend.Ketthuchocphan.Repository.CategoryRepository;
import com.frontend.Ketthuchocphan.Repository.ProductRepository;
import com.frontend.Ketthuchocphan.entity.Category;
import com.frontend.Ketthuchocphan.entity.Product;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    // List all products
    @GetMapping
    public String index(Model model) {
        try {
            List<Product> productList = productRepository.findAll();
            model.addAttribute("productList", productList);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/product/index";
    }

    // Show product details
    @GetMapping("/details/{id}")
    public String details(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
                return "redirect:/admin/products";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products";
        }
        return "admin/product/details";
    }

    // Show create product form
    @GetMapping("/create")
    public String create(Model model) {
        try {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/product/create";
    }

    // Create a new product
    @PostMapping("/create")
    public String create(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            if (product.getCategory() != null && product.getCategory().getId() > 0) {
                productRepository.save(product);
                redirectAttributes.addFlashAttribute("successMessage", "Product created successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Category is required");
                return "redirect:/admin/products/create";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + ex.getMessage());
            return "redirect:/admin/products/create";
        }
        return "redirect:/admin/products/create"; // Redirect to the product list or another appropriate page
    }

    // Show edit product form
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());

                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("categories", categories);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
                return "redirect:/admin/products";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products";
        }
        return "admin/product/edit";
    }

    // Update product
    @PostMapping("/edit")
    public String editProduct(@RequestParam int id,
                              @RequestParam String productName,
                              @RequestParam String product_img,
                              @RequestParam BigDecimal regularPrice,
                              @RequestParam BigDecimal discountPrice,
                              @RequestParam int quantity,
                              @RequestParam String productDescription,
                              @RequestParam boolean active,
                              @RequestParam int categoryId,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setProductName(productName);
                product.setproduct_img(product_img);
                product.setRegularPrice(regularPrice);
                product.setDiscountPrice(discountPrice);
                product.setQuantity(quantity);
                product.setProductDescription(productDescription);
                product.setActive(active);

                Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
                product.setCategory(category);

                productRepository.save(product);
                redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products/edit/" + id;
        }
        return "redirect:/admin/products";
    }

    // Show delete product form
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                model.addAttribute("product", product.get());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
                return "redirect:/admin/products";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/products";
        }
        return "admin/product/delete";
    }

    // Delete a product
    @PostMapping("/delete/{id}")
    public String deleteConfirmed(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                productRepository.delete(product.get());
                redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Product not found");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/admin/products";
    }
}
