package gestion_restaurant.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Livreur;
import gestion_restaurant.entity.RoleType;
import gestion_restaurant.repository.LivreurRepository;

public class LivreurRepositoryImpl implements LivreurRepository {

    private final UserRepositoryImpl userRepo = new UserRepositoryImpl();

    public Livreur save(Livreur l) throws Exception {
        l.setRole(RoleType.LIVREUR);
        userRepo.save(l);
        String sql = """
            INSERT INTO livreur(id, matricule_moto, disponible)
            VALUES (?, ?, ?)
        """;

        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, l.getId());
            ps.setString(2, l.getMatriculeMoto());
            ps.setBoolean(3, l.isDisponible());

            ps.executeUpdate();
        }
        return l;
    }

    public Livreur update(Livreur l) throws Exception {
        userRepo.update(l);

        String sql = """
            UPDATE livreur SET matricule_moto=?, disponible=?
            WHERE id=?
        """;

        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, l.getMatriculeMoto());
            ps.setBoolean(2, l.isDisponible());
            ps.setLong(3, l.getId());
            ps.executeUpdate();
        }
        return l;
    }

    public void delete(Long id) throws Exception {
        userRepo.delete(id);
    }

    public Livreur findById(Long id) throws Exception {
        String sql = """
            SELECT u.*, l.matricule_moto, l.disponible
            FROM users u JOIN livreur l ON u.id = l.id
            WHERE u.id=?
        """;

        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Livreur l = new Livreur();
                l.setId(id);
                l.setNom(rs.getString("nom"));
                l.setPrenom(rs.getString("prenom"));
                l.setTelephone(rs.getString("telephone"));
                l.setLogin(rs.getString("login"));
                l.setPassword(rs.getString("password"));
                l.setRole(RoleType.valueOf(rs.getString("role")));
                l.setCreatedAt(rs.getTimestamp("created_at").toInstant());
                l.setMatriculeMoto(rs.getString("matricule_moto"));
                l.setDisponible(rs.getBoolean("disponible"));
                return l;
            }
        }
        return null;
    }

    public List<Livreur> findAll() throws Exception {
        List<Livreur> list = new ArrayList<>();

        String sql = """
            SELECT u.*, l.matricule_moto, l.disponible
            FROM users u JOIN livreur l ON u.id = l.id
            ORDER BY u.id
        """;
        try (Connection c = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Livreur l = new Livreur();
                l.setId(rs.getLong("id"));
                l.setNom(rs.getString("nom"));
                l.setPrenom(rs.getString("prenom"));
                l.setTelephone(rs.getString("telephone"));
                l.setLogin(rs.getString("login"));
                l.setPassword(rs.getString("password"));
                l.setRole(RoleType.valueOf(rs.getString("role")));
                l.setMatriculeMoto(rs.getString("matricule_moto"));
                l.setDisponible(rs.getBoolean("disponible"));
                list.add(l);
            }
        }
        return list;
    }
}

