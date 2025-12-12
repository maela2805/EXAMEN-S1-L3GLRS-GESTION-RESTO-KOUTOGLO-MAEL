package gestion_restaurant.service;

import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;

import java.util.List;

public interface ComplementService {
    Complement create(Complement c) throws Exception;
    Complement update(Long id, Complement c) throws Exception;
    Complement findById(Long id) throws Exception;
    List<Complement> findAll() throws Exception;
    List<Complement> findByType(ComplementType type) throws Exception;
    void delete(Long id) throws Exception;
}
