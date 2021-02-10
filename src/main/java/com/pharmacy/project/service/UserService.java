package com.pharmacy.project.service;

import com.pharmacy.project.model.Order;
import com.pharmacy.project.model.Role;
import com.pharmacy.project.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname);

    List<Order> userOrders(String remoteUser);

    void addCredits(String username, Integer credits);
}
