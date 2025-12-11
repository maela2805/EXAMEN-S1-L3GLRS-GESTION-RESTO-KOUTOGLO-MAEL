package gestion_restaurant.repository;

import gestion_restaurant.entity.Product;
import gestion_restaurant.entity.Burger;

import java.util.List;

public interface ProductRepository {

    Product save(Product p) throws Exception;

    Product update(Product p) throws Exception;

    Product findById(Long id) throws Exception;

    List<Burger> findAllBurgers() throws Exception;

    void delete(Long id) throws Exception;
}
