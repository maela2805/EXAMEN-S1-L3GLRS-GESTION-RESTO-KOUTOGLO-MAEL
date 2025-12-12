package gestion_restaurant.repository;

import gestion_restaurant.entity.Livreur;
import java.util.List;

public interface LivreurRepository {
    Livreur save(Livreur l) throws Exception;
    Livreur update(Livreur l) throws Exception;
    Livreur findById(Long id) throws Exception;
    List<Livreur> findAll() throws Exception;
    void delete(Long id) throws Exception;
}

