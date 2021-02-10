package com.pharmacy.project.service;

import com.pharmacy.project.model.Category;
import com.pharmacy.project.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getAll();

    List<Manufacturer> getTop();

    Manufacturer addManufacturer(String name, String country);

    Manufacturer editManufacturer(Long id, String name, String country);

    Manufacturer findById(Long id);

    Manufacturer deleteManufacturer(Long id);

    List<String> getAllNames();

    List<Integer> getProdByMan();
}
