package com.pharmacy.project.model;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "pharmacy_orders")
public class Order {
    public Order() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String toAddress;
    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private DeliveryCompany deliveryCompany;

    public Order(User user, String toAddress, List<Product> products, DeliveryCompany deliveryCompany) {
        this.user = user;
        this.toAddress = toAddress;
        this.products = products;
        this.deliveryCompany = deliveryCompany;
        this.dateCreated = LocalDateTime.now();
    }

    public String getDateCreated() {
        return dateCreated.toString()
                .split("T")[0];
    }

    public Integer nOfProds() {
        return products.size();
    }
}
