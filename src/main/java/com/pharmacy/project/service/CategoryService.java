package com.pharmacy.project.service;

import com.pharmacy.project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category addCategory(String name, String desc);

    Category deleteCategory(Long id);

    Category findById(Long id);

    Category editCategory(Long id, String name, String desc);

    List<Integer> getProdByCat();

    List<String> getAllNames();
}
