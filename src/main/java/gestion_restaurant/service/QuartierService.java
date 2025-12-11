package gestion_restaurant.service;

import gestion_restaurant.entity.Quartier;
import java.util.List;

public interface QuartierService {
    Quartier create(Quartier quartier) throws Exception;
    Quartier findById(Integer id) throws Exception;
    List<Quartier> findAll() throws Exception;
    List<Quartier> findByZoneId(Integer zoneId) throws Exception;
    Quartier update(Integer id, Quartier quartier) throws Exception;
    void delete(Integer id) throws Exception;
}
