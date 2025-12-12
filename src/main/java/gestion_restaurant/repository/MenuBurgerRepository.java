package gestion_restaurant.repository;
import gestion_restaurant.entity.MenuBurger;
import java.util.List;

public interface MenuBurgerRepository {
    MenuBurger save(MenuBurger mb) throws Exception;
    List<MenuBurger> findByMenuId(Long menuId) throws Exception;
    void deleteByMenuId(Long menuId) throws Exception;
}
