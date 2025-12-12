package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.MenuComplement;
import gestion_restaurant.repository.MenuComplementRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuComplementRepositoryImpl implements MenuComplementRepository {

    @Override
    public MenuComplement save(MenuComplement mc) throws Exception {
        if (mc == null) return null;

        if (mc.getId() == null) {
            String sql = "INSERT INTO menu_complement (menu_id, complement_id, quantite, role) VALUES (?, ?, ?, ?) RETURNING id";
            try (Connection c = DataSourceProvider.getDataSource().getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, mc.getMenuId());
                ps.setLong(2, mc.getComplementId());
                ps.setInt(3, mc.getQuantite() == null ? 1 : mc.getQuantite());
                ps.setString(4, mc.getRole());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) mc.setId(rs.getLong("id"));
                }
            }
        } else {
            String sql = "UPDATE menu_complement SET menu_id = ?, complement_id = ?, quantite = ?, role = ? WHERE id = ?";
            try (Connection c = DataSourceProvider.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, mc.getMenuId());
                ps.setLong(2, mc.getComplementId());
                ps.setInt(3, mc.getQuantite() == null ? 1 : mc.getQuantite());
                ps.setString(4, mc.getRole());
                ps.setLong(5, mc.getId());
                ps.executeUpdate();
            }
        }
        return mc;
    }


    @Override
    public void deleteByMenuId(Long menuId) throws Exception {
        if (menuId == null) return;
        String sql = "DELETE FROM menu_complement WHERE menu_id = ?";
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, menuId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<MenuComplement> findByMenuId(Long menuId) throws Exception {
        List<MenuComplement> out = new ArrayList<>();
        if (menuId == null) return out;
        String sql = "SELECT id, menu_id, complement_id, quantite, role FROM menu_complement WHERE menu_id = ?";
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, menuId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MenuComplement mc = new MenuComplement();
                    mc.setId(rs.getLong("id"));
                    mc.setMenuId(rs.getLong("menu_id"));
                    mc.setComplementId(rs.getLong("complement_id"));
                    mc.setQuantite(rs.getInt("quantite"));
                    mc.setRole(rs.getString("role"));
                    out.add(mc);
                }
            }
        }
        return out;
    }
}
