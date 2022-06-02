package com.arthe.store.customerservice.service.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthe.store.customerservice.repository.RegionRepository;
import com.arthe.store.customerservice.repository.entity.Region;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegionServiceImp implements RegionService {
    
    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<Region> findRegionAll() { return regionRepository.findAll(); }

    @Override
    public Region createRegion(Region region) {
        Region regionDB = regionRepository.findByName( region.getName() );
        if ( regionDB != null ) {
            return regionDB;
        }
        region.setState("CREATE");
        regionDB = regionRepository.save( region );
        return regionDB;
    }

    @Override
    public Region updateRegion(Region region){
        Region regionDB = getRegion( region.getId() );
        if (regionDB == null) {
            return null;
        }
        region.setState("UPDATE");
        regionDB.setName( region.getName() );

        return regionRepository.save( regionDB );
    }

    @Override
    public Region deleteRegion(Region region){
        Region regionDB = getRegion( region.getId() );
        if ( regionDB == null){
            return null;
        }
        region.setState("DELETED");

        return regionRepository.save( region );
    }

    @Override
    public Region getRegion(Long id) { 
        return regionRepository.findById(id).orElse(null);
    }

}
