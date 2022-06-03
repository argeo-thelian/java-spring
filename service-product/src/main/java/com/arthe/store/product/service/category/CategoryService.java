package com.arthe.store.product.service.category;

import java.util.List;

import com.arthe.store.product.entity.Category;

public interface CategoryService {
    public List<Category> findCategoryAll();

    public Category createCategory(Category category);
    public Category updateCategory(Category category);
    public Category deleteCategory(Category category);
    public Category getCategory(Long id);
}
