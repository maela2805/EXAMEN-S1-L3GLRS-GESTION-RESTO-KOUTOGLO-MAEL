package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Quartier;
import gestion_restaurant.entity.Zone;
import gestion_restaurant.repository.QuartierRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuartierRepositoryImpl implements QuartierRepository {

    private final DataSource ds = DataSourceProvider.getDataSource();

    @Override
    public Quartier save(Quartier q) throws SQLException {

        if (q.getId() == null) {
            String sql = "INSERT INTO quartier (libelle, zone_id) VALUES (?, ?) RETURNING id";

            try (Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setString(1, q.getLibelle());

                if (q.getZone() != null && q.getZone().getId() != null)
                    ps.setInt(2, q.getZone().getId());
                else
                    ps.setNull(2, Types.INTEGER);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) q.setId(rs.getInt("id"));
                }
            }

        } else {

            String sql = "UPDATE quartier SET libelle = ?, zone_id = ? WHERE id = ?";

            try (Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

                ps.setString(1, q.getLibelle());

                if (q.getZone() != null && q.getZone().getId() != null)
                    ps.setInt(2, q.getZone().getId());
                else
                    ps.setNull(2, Types.INTEGER);

                ps.setInt(3, q.getId());
                ps.executeUpdate();
            }
        }

        return q;
    }

    @Override
    public List<Quartier> findAll() throws SQLException {
        List<Quartier> list = new ArrayList<>();

        String sql = """
                SELECT q.id, q.libelle, q.zone_id, z.tarif
                FROM quartier q 
                LEFT JOIN zone z ON q.zone_id = z.id
                ORDER BY q.id
                """;

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Quartier q = new Quartier();
                q.setId(rs.getInt("id"));
                q.setLibelle(rs.getString("libelle"));

                int zoneId = rs.getInt("zone_id");
                if (!rs.wasNull()) {
                    Zone z = new Zone();
                    z.setId(zoneId);
                    z.setTarif(rs.getBigDecimal("tarif"));
                    q.setZone(z);
                }

                list.add(q);
            }
        }

        return list;
    }

    @Override
    public List<Quartier> findByZoneId(int zoneId) throws SQLException {
        List<Quartier> list = new ArrayList<>();

        String sql = "SELECT id, libelle FROM quartier WHERE zone_id = ? ORDER BY id";

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, zoneId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Quartier q = new Quartier();
                    q.setId(rs.getInt("id"));
                    q.setLibelle(rs.getString("libelle"));
                    list.add(q);
                }
            }
        }

        return list;
    }

    @Override
    public Quartier findById(Integer id) throws SQLException {

        String sql = """
                SELECT q.id, q.libelle, q.zone_id, z.tarif
                FROM quartier q 
                LEFT JOIN zone z ON q.zone_id = z.id
                WHERE q.id = ?
                """;

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Quartier q = new Quartier();
                    q.setId(rs.getInt("id"));
                    q.setLibelle(rs.getString("libelle"));

                    int zid = rs.getInt("zone_id");
                    if (!rs.wasNull()) {
                        Zone z = new Zone();
                        z.setId(zid);
                        z.setTarif(rs.getBigDecimal("tarif"));
                        q.setZone(z);
                    }

                    return q;
                }
            }
        }

        return null;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        String sql = "DELETE FROM quartier WHERE id = ?";

        try (Connection c = ds.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
