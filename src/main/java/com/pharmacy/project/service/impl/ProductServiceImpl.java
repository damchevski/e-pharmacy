package com.pharmacy.project.service.impl;

import com.pharmacy.project.model.Category;
import com.pharmacy.project.model.Manufacturer;
import com.pharmacy.project.model.Product;
import com.pharmacy.project.model.exceptions.CategoryNotFoundException;
import com.pharmacy.project.model.exceptions.ManufacturerNotFoundException;
import com.pharmacy.project.model.exceptions.ProductNotFoundException;
import com.pharmacy.project.repository.CategoryRepository;
import com.pharmacy.project.repository.ManufacturerRepository;
import com.pharmacy.project.repository.ProductRepository;
import com.pharmacy.project.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product addProduct(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId, Integer mg) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(ManufacturerNotFoundException::new);

        Product product = new Product(name, price, quantity, mg, category, manufacturer);
        this.productRepository.save(product);

        manufacturer.plusMedicines(quantity);
        this.manufacturerRepository.save(manufacturer);

        return product;
    }

    @Override
    public List<Product> findForIds(List<Long> productsIds) {
        List<Product> products = new ArrayList<>();
        for (Long id : productsIds) {
            products.add(this.productRepository.findById(id)
                    .orElseThrow(ProductNotFoundException::new));
        }

        return products;
    }

    @Override
    public Double productsPrice(List<Product> products) {
        return products.stream()
                .mapToDouble(p -> p.getPrice()).sum();
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(product);

        return product;
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Product editProduct(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId, Integer mg) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(ManufacturerNotFoundException::new);

        product.setCategory(category);
        product.setManufacturer(manufacturer);
        product.setMg(mg);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        this.productRepository.save(product);

        return product;
    }

}
