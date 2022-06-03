package com.arthe.store.product;

import com.arthe.store.product.entity.Category;
import com.arthe.store.product.entity.Product;
import com.arthe.store.product.repository.ProductRepository;
import com.arthe.store.product.service.product.ProductService;
import com.arthe.store.product.service.product.ProductServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Documented;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;
    
    @BeforeEach
    public void setuo(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.3"))
                .stock(Double.parseDouble("5"))
                .build();
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidaGetID_ThenReturnProduct(){
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock(){
        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }
}
