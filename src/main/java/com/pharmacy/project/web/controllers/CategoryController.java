package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.Category;
import com.pharmacy.project.model.Product;
import com.pharmacy.project.service.CategoryService;
import com.pharmacy.project.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public CategoryController(CategoryService categoryService, ManufacturerService manufacturerService) {
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String allCategories(Model model) {
        model.addAttribute("categories", this.categoryService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "category/categories.html";
    }

    @GetMapping("/create")
    public String getCreateNew(Model model) {
        model.addAttribute("top", this.manufacturerService.getTop());
        return "category/createNewCategory.html";
    }

    @PostMapping("/create")
    public String createNew(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam String desc) {

        if (id == null)
            this.categoryService.addCategory(name, desc);
        else this.categoryService.editCategory(id, name, desc);

        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable Long id, Model model) {
        Category category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        model.addAttribute("top", this.manufacturerService.getTop());
        return "category/createNewCategory.html";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteCategory(id);
        return "redirect:/categories";
    }


}
