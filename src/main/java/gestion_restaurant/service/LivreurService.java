package gestion_restaurant.service;

import java.util.List;

import gestion_restaurant.entity.Livreur;

public interface LivreurService {
    Livreur create(Livreur l) throws Exception;
    Livreur update(Livreur l) throws Exception;
    Livreur findById(Long id) throws Exception;
    List<Livreur> findAll() throws Exception;
    void delete(Long id) throws Exception;
}

