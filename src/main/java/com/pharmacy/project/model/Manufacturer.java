package com.pharmacy.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Manufacturer {
    public Manufacturer() {
    }

    @TableGenerator(name = "man", initialValue = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "man")
    private Long id;
    private String name;
    private String country;
    private Integer nMedicines;

    public void plusMedicines(Integer n) {
        this.nMedicines += n;
    }

    public void minusMedicines(Integer n) {
        this.nMedicines -= n;
    }

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
        this.nMedicines = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
