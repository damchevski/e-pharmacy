package com.pharmacy.project.model.exceptions;

public class InvalidOldPassword extends RuntimeException {
    public InvalidOldPassword() {
        super("Invalid Old Password !");
    }
}
