package com.arthe.store.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.arthe.store.customerservice.repository.entity.Region;



public interface RegionRepository extends JpaRepository<Region, Long>{
    public Region findByName(String name);
}
