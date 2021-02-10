package com.pharmacy.project.web.controllers;

import com.pharmacy.project.model.DeliveryCompany;
import com.pharmacy.project.model.Product;
import com.pharmacy.project.model.User;
import com.pharmacy.project.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final DeliveryCompanyService deliveryCompanyService;
    private final ProductService productService;
    private final UserService userService;
    private final ManufacturerService manufacturerService;

    public OrderController(OrderService orderService, DeliveryCompanyService deliveryCompanyService, ProductService productService, UserService userService, ManufacturerService manufacturerService) {
        this.orderService = orderService;
        this.deliveryCompanyService = deliveryCompanyService;
        this.productService = productService;
        this.userService = userService;
        this.manufacturerService = manufacturerService;
    }

    @PostMapping
    public String startOrder(@RequestParam List<Long> products,
                             Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("user", httpServletRequest.getRemoteUser());

        List<Product> productsList = this.productService.findForIds(products);
        httpServletRequest.getSession().setAttribute("products", productsList);
        Double orderPrice = this.productService.productsPrice(productsList);

        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("deliveryCompanies", deliveryCompanyService.getAll());
        model.addAttribute("top", this.manufacturerService.getTop());
        return "order/createOrder.html";
    }

    @PostMapping("/make")
    public String makeOrder(@RequestParam String username,
                            @RequestParam String toAddress,
                            @RequestParam Long deliveryCompanyId,
                            HttpServletRequest httpServletRequest,
                            Model model) {

        User user = (User) this.userService.loadUserByUsername(username);
        DeliveryCompany deliveryCompany = this.deliveryCompanyService.get(deliveryCompanyId);
        List<Product> products = (List<Product>) httpServletRequest.getSession().getAttribute("products");

        Double orderPrice = deliveryCompany.getPrice() +
                this.productService.productsPrice(products);

        model.addAttribute("orderPrice", orderPrice);
        model.addAttribute("userBalance", user.getBalance());

        if (orderPrice > user.getBalance()) {
            model.addAttribute("top", this.manufacturerService.getTop());
            return "order/failOrder.html";
        }

        this.orderService.placeOrder(username, toAddress, products, deliveryCompanyId);
        model.addAttribute("top", this.manufacturerService.getTop());
        return "order/createOrder2.html";
    }
}
