package com.arthe.store.shoppingservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

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
    
}
