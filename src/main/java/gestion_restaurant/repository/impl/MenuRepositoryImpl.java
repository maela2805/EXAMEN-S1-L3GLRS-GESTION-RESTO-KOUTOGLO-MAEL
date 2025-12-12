package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Menu;
import gestion_restaurant.entity.MenuBurger;
import gestion_restaurant.entity.MenuComplement;
import gestion_restaurant.repository.MenuBurgerRepository;
import gestion_restaurant.repository.MenuComplementRepository;
import gestion_restaurant.repository.MenuRepository;
import gestion_restaurant.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryImpl implements MenuRepository {

    private final ProductRepository productRepo;
    private final MenuBurgerRepository menuBurgerRepo;
    private final MenuComplementRepository menuComplementRepo;

    public MenuRepositoryImpl(ProductRepository productRepo,
                              MenuBurgerRepository menuBurgerRepo,
                              MenuComplementRepository menuComplementRepo) {
        this.productRepo = productRepo;
        this.menuBurgerRepo = menuBurgerRepo;
        this.menuComplementRepo = menuComplementRepo;
    }

    @Override
    public Menu save(Menu menu) throws Exception {
        if (menu.getId() == null) {
            menu = (Menu) productRepo.save(menu);
        } else {
            productRepo.update(menu);
        }
        try (Connection c = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "INSERT INTO menu(id, description) VALUES (?, ?) " +
                         "ON CONFLICT (id) DO UPDATE SET description = EXCLUDED.description";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, menu.getId());
                ps.setString(2, menu.getDescription());
                ps.executeUpdate();
            }
        }
        menuBurgerRepo.deleteByMenuId(menu.getId());
        menuComplementRepo.deleteByMenuId(menu.getId());
        if (menu.getMenuBurgers() != null) {
            for (MenuBurger mb : menu.getMenuBurgers()) {
                mb.setMenu(menu);
                if (mb.getBurger() == null || mb.getBurger().getId() == null) {
                    throw new IllegalArgumentException("MenuBurger.burger.id required");
                }
                menuBurgerRepo.save(mb);
            }
        }
        if (menu.getMenuComplements() != null) {
            for (MenuComplement mc : menu.getMenuComplements()) {
                mc.setMenu(menu);
                if (mc.getComplement() == null || mc.getComplement().getId() == null) {
                    throw new IllegalArgumentException("MenuComplement.complement.id required");
                }
                menuComplementRepo.save(mc);
            }
        }

        return findById(menu.getId());
    }

    @Override
    public Menu findById(Long id) throws Exception {
        if (id == null) return null;
        var p = productRepo.findById(id);
        if (p == null) return null;
        Menu menu = (Menu) p;
        try (Connection c = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT description FROM menu WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        menu.setDescription(rs.getString("description"));
                    }
                }
            }
        }

        menu.setMenuBurgers(new java.util.ArrayList<>(menuBurgerRepo.findByMenuId(id)));
        menu.setMenuComplements(new java.util.ArrayList<>(menuComplementRepo.findByMenuId(id)));

        return menu;
    }

    @Override
    public List<Menu> findAll() throws Exception {
        List<Menu> out = new ArrayList<>();
        try (Connection c = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "SELECT p.id FROM product p JOIN menu m ON p.id = m.id";
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    out.add(findById(id));
                }
            }
        }
        return out;
    }

    @Override
    public void deleteById(Long id) throws Exception {
        if (id == null) return;
        menuBurgerRepo.deleteByMenuId(id);
        menuComplementRepo.deleteByMenuId(id);

        try (Connection c = DataSourceProvider.getDataSource().getConnection()) {
            String sql = "DELETE FROM menu WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        }
        try {
            productRepo.delete(id);
        } catch (Exception ex) {
            
        throw new RuntimeException("Impossible de le produit");
        }
        
    }
    @Override
    public Menu update(Menu menu) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}

    
