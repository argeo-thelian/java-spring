package com.arthe.store.shoppingservice.repository;

import java.util.List;
import com.arthe.store.shoppingservice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findByCustomerId(Long customerId);
    public Invoice findByNumberInvoice(String numberInvoice);
}
