package com.arthe.store.shoppingservice.service;

import java.util.List;
import java.util.stream.Collectors;

import com.arthe.store.shoppingservice.client.CustomerClient;
import com.arthe.store.shoppingservice.client.ProductClient;
import com.arthe.store.shoppingservice.entity.Invoice;
import com.arthe.store.shoppingservice.entity.InvoiceItem;
import com.arthe.store.shoppingservice.model.Customer;
import com.arthe.store.shoppingservice.model.Product;
import com.arthe.store.shoppingservice.repository.InvoiceItemsRepository;
import com.arthe.store.shoppingservice.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService{
    
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemsRepository invoiceItemsRepository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;

    @Override
    public List<Invoice> findInvoiceAll(){
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice){
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice( invoice.getNumberInvoice() );
        if ( invoiceDB != null ){
            return invoiceDB;
        }
        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);
        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStockProduct( invoiceItem.getProductId(), invoiceItem.getQuantity() * -1 );
        });
        return invoiceDB;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {

        Invoice invoice= invoiceRepository.findById(id).orElse(null);
        if (null != invoice ){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            List<InvoiceItem> listItem=invoice.getItems().stream().map(invoiceItem -> {
                Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(listItem);
        }
        return invoice ;
    }

}
