package com.arthe.store.customerservice.controller;

import com.arthe.store.customerservice.repository.entity.Customer;
import com.arthe.store.customerservice.repository.entity.Region;
import com.arthe.store.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerRest {

    @Autowired
    CustomerService customerService;

    //GetAllCustomers
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomer(
            @RequestParam( name = "regionId", required = false) Long regionId ){
        List<Customer> customers = new ArrayList<>();
        if ( null == regionId){
            customers = customerService.findCustomerAll();
            if(customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        } else {
            Region region = new Region();
            region.setId(regionId);
            customers = customerService.findCustomersByRegion(region);
            if ( null == customers ){
                log.error("Customer with Region id {} not found. ", regionId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    //GetOnlyOneCustomers
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        log.info("Fetching Customer with id {}", id);
        Customer customer = customerService.getCustomer(id);
        if ( null == customer ){
            log.error("Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    //CreateACustomer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        log.info("Creating Customer : {}", customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Customer customerDB = customerService.createCustomer( customer );
        return ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    //Update a Customer


    private String formatMessage(BindingResult result) {
    }
}
