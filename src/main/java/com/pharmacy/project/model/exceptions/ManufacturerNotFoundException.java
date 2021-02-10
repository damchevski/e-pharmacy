package com.pharmacy.project.model.exceptions;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException() {
        super("Manufacturer Not Found !");
    }
}
