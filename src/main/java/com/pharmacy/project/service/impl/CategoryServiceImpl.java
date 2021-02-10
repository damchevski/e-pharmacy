package com.pharmacy.project.service.impl;

import com.pharmacy.project.model.Category;
import com.pharmacy.project.model.Product;
import com.pharmacy.project.model.exceptions.CategoryNotFoundException;
import com.pharmacy.project.repository.CategoryRepository;
import com.pharmacy.project.repository.ProductRepository;
import com.pharmacy.project.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category addCategory(String name, String desc) {
        Category category = new Category(name, desc);
        this.categoryRepository.save(category);
        return category;
    }

    @Override
    public Category deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.categoryRepository.delete(category);
        return category;
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Category editCategory(Long id, String name, String desc) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        category.setDescription(desc);
        category.setName(name);

        this.categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Integer> getProdByCat() {
        List<Category> categories = this.getAll();
        List<Product> products = this.productRepository.findAll();

        List<Integer> prodsByCat = new ArrayList<>();
        for (Category cat : categories) {
            prodsByCat.add(products.stream()
                    .filter(p -> p.getCategory().getId() == cat.getId())
                    .collect(Collectors.toList())
                    .size());
        }

        return prodsByCat;
    }

    @Override
    public List<String> getAllNames() {
        return this.categoryRepository.findAll()
                .stream().map(c -> c.getName())
                .collect(Collectors.toList());
    }
}
