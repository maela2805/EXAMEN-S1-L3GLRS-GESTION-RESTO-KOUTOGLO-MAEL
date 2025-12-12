package gestion_restaurant.service;

import gestion_restaurant.entity.Complement;
import java.io.File;
import java.util.List;

public interface ComplementService {
    Complement create(Complement c, File imageFile) throws Exception;
    Complement update(Long id, Complement c, File imageFile) throws Exception;
    List<Complement> findAll() throws Exception;
    Complement findById(Long id) throws Exception;
    void delete(Long id) throws Exception;
}
