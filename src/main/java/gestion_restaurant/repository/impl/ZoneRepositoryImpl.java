package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Zone;
import gestion_restaurant.repository.ZoneRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneRepositoryImpl implements ZoneRepository {

    private final DataSource ds = DataSourceProvider.getDataSource();


    @Override
    public Zone save(Zone zone) throws SQLException {
        if (zone.getId() == null) {
            String sql = "INSERT INTO zone (tarif) VALUES (?) RETURNING id";
            try (Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setBigDecimal(1, zone.getTarif());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) zone.setId(rs.getInt("id"));
                }
            }
        } else {
            String sql = "UPDATE zone SET tarif = ? WHERE id = ?";
            try (Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setBigDecimal(1, zone.getTarif());
                ps.setInt(2, zone.getId());
                ps.executeUpdate();
            }
        }
        return zone;
    }

    @Override
    public List<Zone> findAll() throws SQLException {
        List<Zone> list = new ArrayList<>();
        String sql = "SELECT id, tarif FROM zone ORDER BY id";

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Zone z = new Zone();
                z.setId(rs.getInt("id"));
                z.setTarif(rs.getBigDecimal("tarif"));
                list.add(z);
            }
        }
        return list;
    }

    @Override
    public Zone findById(Integer id) throws SQLException {
        String sql = "SELECT id, tarif FROM zone WHERE id = ?";

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Zone z = new Zone();
                    z.setId(rs.getInt("id"));
                    z.setTarif(rs.getBigDecimal("tarif"));
                    return z;
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM zone WHERE id = ?";

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
