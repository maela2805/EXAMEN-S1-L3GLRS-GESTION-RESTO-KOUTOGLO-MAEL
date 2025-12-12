package gestion_restaurant.service;

import gestion_restaurant.entity.Zone;
import java.util.List;

public interface ZoneService {
    Zone create(Zone zone) throws Exception;
    Zone findById(Integer id) throws Exception;
    List<Zone> findAll() throws Exception;
    Zone update(Integer id, Zone zone) throws Exception;
    void delete(Integer id) throws Exception;
}
