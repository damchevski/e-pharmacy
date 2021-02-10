package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.DeliveryCompany;
import com.pharmacy.project.model.Manufacturer;
import com.pharmacy.project.service.DeliveryCompanyService;
import com.pharmacy.project.service.ManufacturerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/deliveryCompanies")
public class DeliveryCompanyController {
    private final DeliveryCompanyService deliveryCompanyService;
    private final ManufacturerService manufacturerService;

    public DeliveryCompanyController(DeliveryCompanyService deliveryCompanyService, ManufacturerService manufacturerService) {
        this.deliveryCompanyService = deliveryCompanyService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String allDeliveryCompanies(Model model) {
        model.addAttribute("deliveryCompanies", this.deliveryCompanyService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "deliveryCompany/deliveryCompanies.html";
    }

    @GetMapping("/create")
    public String getCreateNew(Model model) {
        model.addAttribute("top", this.manufacturerService.getTop());
        return "deliveryCompany/createNewDeliveryCompany.html";
    }

    @PostMapping("/create")
    public String createNew(@RequestParam(required = false) Long id,
                            @RequestParam String name,
                            @RequestParam String country,
                            @RequestParam Integer price) {

        if (id == null)
            this.deliveryCompanyService.addDc(name, country, price);
        else this.deliveryCompanyService.editDc(id, name, country, price);

        return "redirect:/deliveryCompanies";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable Long id, Model model) {
        DeliveryCompany dc = this.deliveryCompanyService.findById(id);
        model.addAttribute("deliveryCompany", dc);
        model.addAttribute("top", this.manufacturerService.getTop());
        return "deliveryCompany/createNewDeliveryCompany.html";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteDc(@PathVariable Long id) {
        this.deliveryCompanyService.deleteDc(id);
        return "redirect:/deliveryCompanies";
    }


}
