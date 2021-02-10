package com.pharmacy.project.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "e_pharmacy_users")
public class User implements UserDetails {
    public User() {
    }

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = Role.ROLE_USER;
        this.balance = 0.0;
    }

    @Id
    private String username;
    private String name;
    private String surname;
    private String password;
    private Double balance;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Double getBalance() {
        return balance;
    }

    public void minusBalance(Double n) {
        this.balance -= n;
    }

    public void plusBalance(Double n) {
        this.balance += n;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Integer ordersN(){
        return orders.size();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
