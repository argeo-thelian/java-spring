package com.arthe.store.customerservice.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.arthe.store.customerservice.repository.entity.Region;
import com.arthe.store.customerservice.service.region.RegionService;
import com.arthe.store.customerservice.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/regions")
public class RegionRest {

    @Autowired
    RegionService regionService;

    Utils utils = new Utils();

    //GetAllRegions
    @GetMapping
    public ResponseEntity<List<Region>> listAllRegion(){
        List<Region> regions = new ArrayList<>();
        regions = regionService.findRegionAll();
        if ( regions.isEmpty() ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    //GetOnlyOneRegion
    @GetMapping( value = "/{id}" )
    public ResponseEntity<Region> getRegion(@PathVariable("id") Long id){
        log.info("Fetching Region with id {} ", id);
        Region region = regionService.getRegion(id);
        if ( null == region){
            log.error("Region with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }
    
    //CreateARegion
    @PostMapping
    public ResponseEntity<Region> createRegion(@Valid @RequestBody Region region, BindingResult result){
        log.info("Creating Region : {} ", region);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, utils.formatMessage(result));
        }
        Region regionDB = regionService.createRegion( region );
        return ResponseEntity.status( HttpStatus.CREATED ).body(regionDB);
    }

    //UpdateARegion
    @PutMapping( value = "/{id}" )
    public ResponseEntity<Region> updateRegion(@PathVariable("id") Long id, @RequestBody Region region){
        log.info("Updating Region with  id: {} ", id);
        Region currentRegion = regionService.getRegion(id);
        if ( null == currentRegion ){
            log.error("Unable to update. Region with  id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        region.setId(id);
        currentRegion = regionService.updateRegion(region);
        return ResponseEntity.ok( currentRegion );
    }

    //Delete a Region
    @DeleteMapping( value = "/{id}" )
    public ResponseEntity<Region> deleteRegion(@PathVariable( "id" ) Long id ){
        log.info("Fetching & Deleting Region with id {} ", id);
        Region region = regionService.getRegion(id);
        if ( null == region ) {
            log.error("Unable to delete. Region with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        region = regionService.deleteRegion(region);
        return ResponseEntity.ok(region);
    }
    
}

