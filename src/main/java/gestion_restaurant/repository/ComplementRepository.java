package gestion_restaurant.repository;

import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;

import java.util.List;

public interface ComplementRepository {
    Complement save(Complement c) throws Exception;
    Complement update(Complement c) throws Exception;
    Complement findById(Long id) throws Exception;
    List<Complement> findAll() throws Exception;
    List<Complement> findByType(ComplementType t) throws Exception;
    boolean delete(Long id) throws Exception;
}
