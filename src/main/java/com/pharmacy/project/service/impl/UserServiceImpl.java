package com.pharmacy.project.service.impl;

import com.pharmacy.project.model.Order;
import com.pharmacy.project.model.Role;
import com.pharmacy.project.model.User;
import com.pharmacy.project.model.exceptions.InvalidOldPassword;
import com.pharmacy.project.model.exceptions.InvalidUsernameOrPasswordException;
import com.pharmacy.project.model.exceptions.PasswordsDoNotMatchException;
import com.pharmacy.project.model.exceptions.UsernameAlreadyExistsException;
import com.pharmacy.project.repository.UserRepository;
import com.pharmacy.project.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if (this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException();

        User user = new User(username, passwordEncoder.encode(password), name, surname);
        return userRepository.save(user);
    }

    @Override
    public List<Order> userOrders(String remoteUser) {
        User u = this.userRepository.findByUsername(remoteUser)
                .orElseThrow(() -> new UsernameNotFoundException(remoteUser));

        return u.getOrders();
    }

    @Override
    public void addCredits(String username, Integer credits) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(com.pharmacy.project.model.exceptions.UsernameNotFoundException::new);

        user.plusBalance(credits.doubleValue());
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
