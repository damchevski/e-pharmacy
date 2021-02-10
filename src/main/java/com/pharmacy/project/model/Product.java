package com.pharmacy.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {
    public Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Integer mg;

    @ManyToOne
    private Category category;
    @ManyToOne
    private Manufacturer manufacturer;

    public Product(String name, Double price, Integer quantity, Integer mg, Category category, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.mg = mg;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public Double getPrice() {
        return price;
    }

    public Integer minusQuantity() {
        this.quantity -= 1;
        return this.quantity;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setMg(Integer mg) {
        this.mg = mg;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
