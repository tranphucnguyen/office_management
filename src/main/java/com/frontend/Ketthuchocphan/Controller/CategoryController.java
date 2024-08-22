package com.frontend.Ketthuchocphan.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.frontend.Ketthuchocphan.Repository.CategoryRepository;
import com.frontend.Ketthuchocphan.entity.Category;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    @GetMapping
    public String index(Model model) {
        try {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("CategoryList", categories);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "admin/category/index";
    }

    // Get category by id
    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                model.addAttribute("Category", category.get());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
                return "redirect:/admin/categories";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/categories";
        }
        return "admin/category/details";
    }

    // Show create category form
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/create";
    }

    // Create new category
    @PostMapping("/create")
    public String createCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        try {
            // Tạo một category đơn lẻ
            categoryRepository.save(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category created successfully");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + ex.getMessage());
        }
        return "redirect:/admin/categories/create";
    }

    // Show edit category form
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                model.addAttribute("category", category.get());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
                return "redirect:/admin/categories";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/categories";
        }
        return "admin/category/edit";
    }

    // Update category
    @PostMapping("/edit")
    public String edit(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> existingCategory = categoryRepository.findById(category.getId());
            if (existingCategory.isPresent()) {
                Category updatedCategory = existingCategory.get();
                updatedCategory.setCategoryName(category.getCategoryName());
                updatedCategory.setActive(category.isActive());
                categoryRepository.save(updatedCategory);
                redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating category: " + ex.getMessage());
        }
        return "redirect:/admin/categories";
    }

    // Show delete category confirmation form
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                model.addAttribute("category", category.get());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
                return "redirect:/admin/categories";
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/admin/categories";
        }
        return "admin/category/delete";
    }

    // Delete category
    @PostMapping("/delete/{id}")
    public String deleteConfirmed(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                categoryRepository.delete(category.get());
                redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting category: " + ex.getMessage());
        }
        return "redirect:/admin/categories";
    }
}
