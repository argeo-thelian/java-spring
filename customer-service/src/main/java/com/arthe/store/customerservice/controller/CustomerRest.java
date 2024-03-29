package com.arthe.store.customerservice.controller;

import com.arthe.store.customerservice.repository.entity.Customer;
import com.arthe.store.customerservice.repository.entity.Region;
import com.arthe.store.customerservice.service.customer.CustomerService;

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
    
    com.arthe.store.customerservice.utils.Utils utils = new com.arthe.store.customerservice.utils.Utils();
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, utils.formatMessage(result));
        }
        Customer customerDB = customerService.createCustomer( customer );
        return ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    //Update a Customer
    @PutMapping( value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        log.info("Updateing Customer with id {}", id);

        Customer currentCustomer = customerService.getCustomer(id);
        if( null == currentCustomer){
            log.error("Unable to update. Cutomer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(currentCustomer);
    }

    //Delete a Customer
    @DeleteMapping( value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable( "id" ) Long id){
        log.info("Fetching & Deleting Customer with id {} ", id);
        Customer customer = customerService.getCustomer(id);
        if( null == customer){
            log.error("Unable to delete. Customer with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    // private String formatMessage(BindingResult result) {
    //     List<Map<String, String>> errors = result.getFieldErrors().stream()
    //         .map(err ->{
    //             Map<String, String> error = new HashMap<>();
    //             error.put(err.getField(), err.getDefaultMessage());
    //             return error;
    //         }).collect(Collectors.toList());
    //     ErrorMessage errorMessage = ErrorMessage.builder()
    //         .code("01")
    //         .messages(errors).build();
    //     ObjectMapper mapper = new ObjectMapper();
    //     String jsonString = "";

    //     try {
    //         jsonString = mapper.writeValueAsString(errorMessage);
    //     } catch (JsonProcessingException e) {
    //         e.printStackTrace();
    //     }
    //     return jsonString.replace("\"","'");         
    // }
}
