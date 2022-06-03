package com.arthe.store.product.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.arthe.store.product.entity.Category;
import com.arthe.store.product.service.category.CategoryService;
import com.arthe.store.product.utils.Utils;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@RestController
@RequestMapping( value =  "/categories" )
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    Utils utils = new Utils();
    //Get All Category
    @GetMapping
    public ResponseEntity<List<Category>> listAllCategory() {
        List<Category> categories = new ArrayList<>();
        categories = categoryService.findCategoryAll();
        if ( categories.isEmpty() ) {
            return ResponseEntity.noContent().build();
        } 
        return ResponseEntity.ok(categories);
    }

    //Get Only One Category
    @GetMapping( value = "/{id}" )
    public ResponseEntity<Category> getCategory(@RequestParam( "id" ) Long id) {
        log.info("Fetching Category with id {} ", id);
        Category category = categoryService.getCategory(id);
        if ( null == category ) {
            log.error("Category with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }   
        return ResponseEntity.ok( category );
    }
    
    //Create a Category
    @PostMapping
    public ResponseEntity<Category> createCategory( 
        @Valid @RequestBody Category category, 
        BindingResult result) 
    {
        log.info("Creating Category : {} ", category);
        if ( result.hasErrors() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, utils.formatMessage(result));
        }
        Category categoryDB = categoryService.createCategory(category);
        return ResponseEntity.status( HttpStatus.CREATED ).body( categoryDB );
    }

    //Update a Category
    @PutMapping( value = "/{id}" )
    public ResponseEntity<Category> updateCategory(
        @PathVariable( "id" ) Long id, 
        @RequestBody Category category
    ) {
        log.info("Updateing Category with id: {} ", id);
        Category currenCategory = categoryService.getCategory( id );
        if ( null == currenCategory ) {
            log.error("Unable to update. Category with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }   
        category.setId(id);
        currenCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok( currenCategory );
    }

    //Delete a Category
    @DeleteMapping( value = "/{id}" )
    public ResponseEntity<Category> deleteCategory(@PathVariable( "id" ) Long id) {
        log.info("Fetching & Deleting Category with id: {}", id);
        Category category = categoryService.getCategory(id);
        if ( null == category ) {
            log.info("Unable to delete. Category with id: {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        category = categoryService.deleteCategory( category );
        return ResponseEntity.ok(category);
    }
}
