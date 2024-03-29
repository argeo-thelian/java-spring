package com.arthe.store.shoppingservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Long id;

    private String numberID;

    private String firstName;

    private String lastName;

    private String email;
    
    private String photoUrl;

    private String region;

    private String state;
}
