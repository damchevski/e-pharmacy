package com.pharmacy.project.model.exceptions;

public class DeliveryCompanyNotFoundException extends RuntimeException {
    public DeliveryCompanyNotFoundException() {
        super("Delivery Company not found !");
    }
}
