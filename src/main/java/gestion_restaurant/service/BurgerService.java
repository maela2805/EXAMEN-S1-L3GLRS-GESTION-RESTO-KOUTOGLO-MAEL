package gestion_restaurant.service;

import gestion_restaurant.entity.Burger;

import java.io.File;
import java.util.List;

public interface BurgerService {

    Burger create(Burger burger, File imageFile) throws Exception;
    Burger updateImage(Long id, File newImage) throws Exception;
    void delete(Long id) throws Exception;
    Burger findById(Long id) throws Exception;
    List<Burger> findAll() throws Exception;
}
