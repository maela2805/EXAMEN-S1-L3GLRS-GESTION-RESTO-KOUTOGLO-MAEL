package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Frite;
import gestion_restaurant.repository.FriteRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class FriteRepositoryImpl implements FriteRepository {
    private final DataSource ds = DataSourceProvider.getDataSource();

    @Override
    public void save(Frite f) throws Exception {
        String sql = "INSERT INTO frite (id, taille) VALUES (?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, f.getId());
            ps.setString(2, f.getTaille());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Frite f) throws Exception {
        String sql = "UPDATE frite SET taille = ? WHERE id = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, f.getTaille());
            ps.setLong(2, f.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws Exception {
        String sql = "DELETE FROM frite WHERE id = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
