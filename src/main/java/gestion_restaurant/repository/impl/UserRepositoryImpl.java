package gestion_restaurant.repository.impl;

import gestion_restaurant.entity.RoleType;
import gestion_restaurant.entity.User;
import gestion_restaurant.repository.UserRepository;
import gestion_restaurant.db.DataSourceProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User u) throws Exception {

        String sql = """
            INSERT INTO users(nom, prenom, telephone, login, password, role)
            VALUES (?, ?, ?, ?, ?, ?::role_type)
            RETURNING id, created_at
        """;
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getTelephone());
            ps.setString(4, u.getLogin());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getRole().name());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getLong("id"));
                u.setCreatedAt(rs.getTimestamp("created_at").toInstant());
            }
        }

        return u;
    }


    @Override
    public User update(User u) throws Exception {
        String sql = """
            UPDATE users SET nom=?, prenom=?, telephone=?, login=?, password=?, role=?::role_type
            WHERE id=?
        """;


        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getTelephone());
            ps.setString(4, u.getLogin());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getRole().name());
            ps.setLong(7, u.getId());
            ps.executeUpdate();
        }
        return u;
    }

    @Override
    public void delete(Long id) throws Exception {
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE id=?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public User findById(Long id) throws Exception {
        String sql = "SELECT * FROM users WHERE id=?";
        User u = null;
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new User() {};
                u.setId(rs.getLong("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setTelephone(rs.getString("telephone"));
                u.setLogin(rs.getString("login"));
                u.setPassword(rs.getString("password"));
                u.setRole(RoleType.valueOf(rs.getString("role")));
                u.setCreatedAt(rs.getTimestamp("created_at").toInstant());
            }
        }
        return u;
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User() {};
                u.setId(rs.getLong("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setTelephone(rs.getString("telephone"));
                u.setLogin(rs.getString("login"));
                u.setPassword(rs.getString("password"));
                u.setRole(RoleType.valueOf(rs.getString("role")));
                list.add(u);
            }
        }
        return list;
    }
}
