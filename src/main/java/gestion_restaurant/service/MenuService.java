package gestion_restaurant.service;
import gestion_restaurant.entity.Menu;
import java.io.File;
import java.util.List;

public interface MenuService {
    Menu create(Menu menu, File imageFile)throws Exception;
    Menu findById(Long id) throws Exception;
    List<Menu> findAll() throws Exception;
    void delete(Long id) throws Exception;
    Menu update(Long id, Menu menu, File imageFile) throws Exception;
}
