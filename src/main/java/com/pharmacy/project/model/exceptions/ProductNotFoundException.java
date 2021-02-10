package com.pharmacy.project.model.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product not found !");
    }
}
