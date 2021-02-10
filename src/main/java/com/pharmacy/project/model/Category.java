package com.pharmacy.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {
    public Category() {
    }

    @TableGenerator(name = "cat", initialValue = 5)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cat")
    private Long id;
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
