package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.Product;
import com.pharmacy.project.service.CategoryService;
import com.pharmacy.project.service.ManufacturerService;
import com.pharmacy.project.service.OrderService;
import com.pharmacy.project.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final OrderService orderService;

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             ManufacturerService manufacturerService,
                             OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.orderService = orderService;
    }

    @GetMapping
    public String allProducts(Model model) {
        model.addAttribute("products", this.productService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "product/allProducts.html";
    }

    @GetMapping("/create")
    public String getCreateNew(Model model) {
        model.addAttribute("categories", this.categoryService.getAll());
        model.addAttribute("manufacturers", this.manufacturerService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "product/createNewProduct.html";
    }

    @PostMapping("/create")
    public String createNew(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam Double price,
                            @RequestParam Integer mg,
                            @RequestParam Integer quantity,
                            @RequestParam Long category,
                            @RequestParam Long manufacturer) {

        if (id == null)
            this.productService.addProduct(name, price, quantity, category, manufacturer, mg);
        else this.productService.editProduct(id, name, price, quantity, category, manufacturer, mg);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable Long id, Model model) {
        Product product = this.productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", this.categoryService.getAll());
        model.addAttribute("manufacturers", this.manufacturerService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "product/createNewProduct.html";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/charts")
    public String getCharts(Model model){
        model.addAttribute("top", this.manufacturerService.getTop());

        model.addAttribute("categories", this.categoryService.getAllNames());
        model.addAttribute("prodByCat", this.categoryService.getProdByCat());

        model.addAttribute("manufacturers", this.manufacturerService.getAllNames());
        model.addAttribute("prodByMan", this.manufacturerService.getProdByMan());

        model.addAttribute("dates", this.orderService.getAllDates());
        model.addAttribute("prodByDate", this.orderService.getProdByDate());

        return "product/charts.html";
    }
}
