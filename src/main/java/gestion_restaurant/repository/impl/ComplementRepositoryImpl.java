package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.repository.ComplementRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.Instant;

public class ComplementRepositoryImpl implements ComplementRepository {

    private final DataSource ds = DataSourceProvider.getDataSource();

    @Override
    public Complement save(Complement c) throws SQLException {
        String sql = "INSERT INTO complement (nom, prix, image, typecomplement, created_at, image_public_id, description) " +
                     "VALUES (?, ?, ?, ?::complement_type, ?, ?, ?) RETURNING id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setBigDecimal(2, c.getPrix() == null ? BigDecimal.ZERO : c.getPrix());
            ps.setString(3, c.getImage());
            ps.setString(4, c.getTypeComplement() == null ? null : c.getTypeComplement().name());
            ps.setTimestamp(5, c.getCreatedAt() == null ? Timestamp.from(Instant.now()) : Timestamp.from(c.getCreatedAt()));
            ps.setString(6, c.getImagePublicId());
            ps.setString(7, c.getDescription());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c.setId(rs.getLong("id"));
                    return c;
                } else {
                    throw new SQLException("Insertion complement a échoué, pas d'id retourné.");
                }
            }
        }
    }

    @Override
    public Complement update(Complement c) throws SQLException {
        String sql = "UPDATE complement SET nom = ?, prix = ?, image = ?, typecomplement = ?::complement_type, image_public_id = ?, description = ? WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setBigDecimal(2, c.getPrix() == null ? BigDecimal.ZERO : c.getPrix());
            ps.setString(3, c.getImage());
            ps.setString(4, c.getTypeComplement() == null ? null : c.getTypeComplement().name());
            ps.setString(5, c.getImagePublicId());
            ps.setString(6, c.getDescription());
            ps.setLong(7, c.getId());
            ps.executeUpdate();
            return c;
        }
    }

    @Override
    public Complement findById(Long id) throws SQLException {
        String sql = "SELECT id, nom, prix, image, typecomplement, created_at, image_public_id, description FROM complement WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToComplement(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Complement> findAll() throws SQLException {
        List<Complement> list = new ArrayList<>();
        String sql = "SELECT id, nom, prix, image, typecomplement, created_at, image_public_id, description FROM complement ORDER BY id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapToComplement(rs));
            }
        }
        return list;
    }

    @Override
    public List<Complement> findByType(ComplementType t) throws SQLException {
        List<Complement> list = new ArrayList<>();
        String sql = "SELECT id, nom, prix, image, typecomplement, created_at, image_public_id, description FROM complement WHERE typecomplement = ?::complement_type ORDER BY id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapToComplement(rs));
            }
        }
        return list;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM complement WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Complement mapToComplement(ResultSet rs) throws SQLException {
        Complement c = new Complement();
        c.setId(rs.getLong("id"));
        c.setNom(rs.getString("nom"));
        c.setPrix(rs.getBigDecimal("prix"));
        c.setImage(rs.getString("image"));
        String t = rs.getString("typecomplement");
        if (t != null) c.setTypeComplement(ComplementType.valueOf(t));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) c.setCreatedAt(ts.toInstant());
        c.setImagePublicId(rs.getString("image_public_id"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
