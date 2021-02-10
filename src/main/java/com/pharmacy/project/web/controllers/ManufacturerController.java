package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.Category;
import com.pharmacy.project.model.Manufacturer;
import com.pharmacy.project.service.CategoryService;
import com.pharmacy.project.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String allManufacturers(Model model) {
        model.addAttribute("manufacturers", this.manufacturerService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "manufacturer/manufacturers.html";
    }

    @GetMapping("/create")
    public String getCreateNew(Model model) {
        model.addAttribute("top", this.manufacturerService.getTop());
        return "manufacturer/createNewManufacturer.html";
    }

    @PostMapping("/create")
    public String createNew(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam String country) {

        if (id == null)
            this.manufacturerService.addManufacturer(name, country);
        else this.manufacturerService.editManufacturer(id, name, country);

        return "redirect:/manufacturers";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = this.manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("top", this.manufacturerService.getTop());
        return "manufacturer/createNewManufacturer.html";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteManufacturer(@PathVariable Long id) {
        this.manufacturerService.deleteManufacturer(id);
        return "redirect:/manufacturers";
    }
}
