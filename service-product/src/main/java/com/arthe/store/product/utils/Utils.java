package com.arthe.store.product.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.arthe.store.product.controller.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
            .map(err ->{
                Map<String, String> error = new HashMap<>();
                error.put(err.getField(), err.getDefaultMessage());
                return error;
            }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
            .code("01")
            .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString.replace("\"","'");         
    }
}
