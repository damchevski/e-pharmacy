package com.pharmacy.project.service;

import com.pharmacy.project.model.Order;
import com.pharmacy.project.model.Product;

import java.util.List;

public interface OrderService {
    Order placeOrder(String username, String toAddress, List<Product> products, Long deliveryCompanyId);

    List<String> getAllDates();

    List<Integer> getProdByDate();
}
