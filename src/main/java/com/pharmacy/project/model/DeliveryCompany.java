package com.pharmacy.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DeliveryCompany {
    public DeliveryCompany() {
    }

    @TableGenerator(name = "dc", initialValue = 3)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dc")
    private Long id;
    private String name;
    private String country;
    private Integer price;
    private Integer nAvailable;

    public Integer getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public DeliveryCompany(String name, String country, Integer price) {
        this.name = name;
        this.country = country;
        this.price = price;
        this.nAvailable = 1000;
    }
}
