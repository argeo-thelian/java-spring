package com.arthe.store.product.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthe.store.product.entity.Category;
import com.arthe.store.product.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findCategoryAll(){return categoryRepository.findAll(); }

    @Override
    public Category createCategory(Category category){
        Category categoryDB = categoryRepository.findByName( category.getName() );
        if ( categoryDB != null ) {
            return categoryDB;
        }
        category.setStatus("CREATE");
        categoryDB = categoryRepository.save( category );
        return categoryDB;
    }
    
    @Override
    public Category updateCategory(Category category){
        Category categoryDB = getCategory( category.getId() );
        if ( null == categoryDB ) {
            return null;
        }
        category.setStatus("UPDATE");
        categoryDB.setName( category.getName() );
        return categoryRepository.save( categoryDB );
    }
    
    @Override
    public Category deleteCategory(Category category){
        Category categoryDB = getCategory( category.getId() );
        if ( categoryDB == null ) {
            return null;
        }
        category.setStatus("DELETED");
        return categoryRepository.save( category );
    }
    
    @Override
    public Category getCategory(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
}
