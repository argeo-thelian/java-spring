package com.arthe.store.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arthe.store.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    public Category findByName(String name);
}
