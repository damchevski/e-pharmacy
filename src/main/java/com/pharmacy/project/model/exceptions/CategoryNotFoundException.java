package com.pharmacy.project.model.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Category Not Found !");
    }
}
