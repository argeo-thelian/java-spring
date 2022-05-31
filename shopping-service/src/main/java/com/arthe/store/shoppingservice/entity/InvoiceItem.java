package com.arthe.store.shoppingservice.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.arthe.store.shoppingservice.model.Product;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
@Entity
@Table( name = "tbl_invoice_items")
public class InvoiceItem {
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Positive( message = "El stock debe ser mayor que cero" )
    private Double quantity;

    private Double price;

    @Column( name = "product_id" )
    private Long productId;

    @Transient
    private Product product;

    @Transient
    private Double getSubTotal(){
        if (this.price > 0 && this.quantity > 0) {
            return this.quantity * this.price;
        } else {
            return (double) 0;
        }
    }

    public InvoiceItem(){
        this.quantity = (double) 0 ;
        this.price = (double) 0 ;
    }
}
