package com.pharmacy.project.model.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("Username is not found !");
    }
}
