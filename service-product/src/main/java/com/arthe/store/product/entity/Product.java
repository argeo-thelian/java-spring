package com.arthe.store.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table( name = "tbl_products" )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty( message = "El nombre del campo no debe ser vacío." )
    @Column( name = "name", nullable = false )
    private String name;
    
    
    private String description;
    
    @Positive( message = "El stock debe ser mayor que cero.")
    private Double stock;

    private Double price;
    
    private String status;

    @Column( name = "create_at" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date createAt;

    @NotNull( message = "La categoría no puede ser vacía.")
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "category_id" )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;
}
