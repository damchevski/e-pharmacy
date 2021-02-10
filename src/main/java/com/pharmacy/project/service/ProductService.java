package com.pharmacy.project.service;

import com.pharmacy.project.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product addProduct(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId, Integer mg);

    List<Product> findForIds(List<Long> productsIds);

    Double productsPrice(List<Product> products);

    Product deleteProduct(Long id);

    Product findById(Long id);

    Product editProduct(Long id, String name, Double price, Integer quantity, Long category, Long manufacturer, Integer mg);
}
