package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.MenuBurger;
import gestion_restaurant.repository.MenuBurgerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuBurgerRepositoryImpl implements MenuBurgerRepository {

     @Override
    public MenuBurger save(MenuBurger mb) throws Exception {
        if (mb == null){
            return null;
        }
        if (mb.getId() == null) {
            String sql = "INSERT INTO menu_burger (menu_id, burger_id, quantite) VALUES (?, ?, ?) RETURNING id";
            try (Connection c = DataSourceProvider.getDataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, mb.getMenuId());
                ps.setLong(2, mb.getBurgerId());
                if (mb.getQuantite() == null){
                    ps.setInt(3, 1);
                }
                else ps.setInt(3, mb.getQuantite());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        mb.setId(rs.getLong("id"));
                    }
                }
            }
        } else {
            String sql = "UPDATE menu_burger SET menu_id = ?, burger_id = ?, quantite = ? WHERE id = ?";
            try (Connection c = DataSourceProvider.getDataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, mb.getMenuId());
                ps.setLong(2, mb.getBurgerId());
                ps.setInt(3, mb.getQuantite() == null ? 1 : mb.getQuantite());
                ps.setLong(4, mb.getId());
                ps.executeUpdate();
            }
        }
        return mb;
    }


    @Override
    public void deleteByMenuId(Long menuId) throws Exception {
        if (menuId == null) return;
        String sql = "DELETE FROM menu_burger WHERE menu_id = ?";
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, menuId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<MenuBurger> findByMenuId(Long menuId) throws Exception {
        List<MenuBurger> out = new ArrayList<>();
        if (menuId == null) return out;
        String sql = "SELECT id, menu_id, burger_id, quantite FROM menu_burger WHERE menu_id = ?";
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MenuBurger mb = new MenuBurger();
                    mb.setId(rs.getLong("id"));
                    mb.setMenuId(rs.getLong("menu_id"));
                    mb.setBurgerId(rs.getLong("burger_id"));
                    mb.setQuantite(rs.getInt("quantite"));
                    out.add(mb);
                }
            }
        }
        return out;
    }
}
