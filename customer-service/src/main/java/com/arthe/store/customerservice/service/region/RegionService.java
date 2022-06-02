package com.arthe.store.customerservice.service.region;

import java.util.List;

import com.arthe.store.customerservice.repository.entity.Region;

public interface RegionService {
    public List<Region> findRegionAll();

    public Region createRegion(Region region);
    public Region updateRegion(Region region);
    public Region deleteRegion(Region region);
    public Region getRegion(Long id);
}
