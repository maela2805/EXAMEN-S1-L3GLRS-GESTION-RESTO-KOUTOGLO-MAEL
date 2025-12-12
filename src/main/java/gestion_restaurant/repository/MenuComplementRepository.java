package gestion_restaurant.repository;

import gestion_restaurant.entity.MenuComplement;
import java.util.List;

public interface MenuComplementRepository {
    MenuComplement save(MenuComplement mc) throws Exception;
    List<MenuComplement> findByMenuId(Long menuId) throws Exception;
    void deleteByMenuId(Long menuId) throws Exception;
}
