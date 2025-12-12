package gestion_restaurant.repository;
import gestion_restaurant.entity.Menu;
import java.util.List;

public interface MenuRepository {
    Menu save(Menu menu) throws Exception;
    Menu update(Menu menu) throws Exception;
    Menu findById(Long id) throws Exception;
    List<Menu> findAll() throws Exception;
    void delete(Long id) throws Exception;
    void deleteById(Long id) throws Exception;

}
