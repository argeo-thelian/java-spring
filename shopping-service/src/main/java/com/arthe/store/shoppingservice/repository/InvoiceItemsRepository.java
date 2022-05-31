package com.arthe.store.shoppingservice.repository;

import com.arthe.store.shoppingservice.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem, Long>{
}
