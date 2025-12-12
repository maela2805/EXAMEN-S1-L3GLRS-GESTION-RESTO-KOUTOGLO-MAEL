package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Boisson;
import gestion_restaurant.repository.BoissonRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BoissonRepositoryImpl implements BoissonRepository{
    private final DataSource ds = DataSourceProvider.getDataSource();

    @Override
    public void save(Boisson b) throws Exception {
        String sql = "INSERT INTO frite (id, taille) VALUES (?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, b.getId());
            ps.setString(2, b.getVolume());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Boisson b) throws Exception {
        String sql = "UPDATE frite SET taille = ? WHERE id = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, b.getVolume());
            ps.setLong(2, b.getId());
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
