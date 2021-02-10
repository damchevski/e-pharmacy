package com.pharmacy.project.service.impl;

import com.pharmacy.project.model.Category;
import com.pharmacy.project.model.Manufacturer;
import com.pharmacy.project.model.Product;
import com.pharmacy.project.model.exceptions.ManufacturerNotFoundException;
import com.pharmacy.project.repository.ManufacturerRepository;
import com.pharmacy.project.repository.ProductRepository;
import com.pharmacy.project.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ProductRepository productRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Manufacturer> getAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public List<Manufacturer> getTop() {
        return this.manufacturerRepository.getTop();
    }

    @Override
    public Manufacturer addManufacturer(String name, String country) {
        Manufacturer manufacturer = new Manufacturer(name, country);

        this.manufacturerRepository.save(manufacturer);
        return manufacturer;
    }

    @Override
    public Manufacturer editManufacturer(Long id, String name, String country) {
        Manufacturer manufacturer = this.manufacturerRepository.findById(id)
                .orElseThrow(ManufacturerNotFoundException::new);

        manufacturer.setCountry(country);
        manufacturer.setName(name);

        this.manufacturerRepository.save(manufacturer);
        return manufacturer;
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id)
                .orElseThrow(ManufacturerNotFoundException::new);
    }

    @Override
    public Manufacturer deleteManufacturer(Long id) {
        Manufacturer manufacturer = this.manufacturerRepository.findById(id)
                .orElseThrow(ManufacturerNotFoundException::new);

        this.manufacturerRepository.delete(manufacturer);
        return manufacturer;
    }

    @Override
    public List<String> getAllNames() {
        return this.manufacturerRepository.findAll()
                .stream().map(c -> c.getName())
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getProdByMan() {
        List<Manufacturer> manufacturers = this.getAll();
        List<Product> products = this.productRepository.findAll();

        List<Integer> prodsByMan = new ArrayList<>();
        for (Manufacturer man : manufacturers) {
            prodsByMan.add(products.stream()
                    .filter(p -> p.getManufacturer().getId() == man.getId())
                    .mapToInt(p -> p.getQuantity()).sum());
        }

        return prodsByMan;
    }


}
