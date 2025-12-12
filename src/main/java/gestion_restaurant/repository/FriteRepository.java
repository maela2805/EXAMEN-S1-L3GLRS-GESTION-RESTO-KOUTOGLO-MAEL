package gestion_restaurant.repository;

import gestion_restaurant.entity.Frite;

public interface FriteRepository {
    void save(Frite f) throws Exception;
    void update(Frite f) throws Exception;
    void deleteById(Long id) throws Exception;
}
