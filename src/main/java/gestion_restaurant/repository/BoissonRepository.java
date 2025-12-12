package gestion_restaurant.repository;

import gestion_restaurant.entity.Boisson;

public interface BoissonRepository {
    void save(Boisson b) throws Exception;
    void update(Boisson b) throws Exception;
    void deleteById(Long id) throws Exception;
}
