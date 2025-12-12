package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.entity.Frite;
import gestion_restaurant.entity.Boisson;
import gestion_restaurant.repository.ComplementRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplementRepositoryImpl implements ComplementRepository {

    private final DataSource ds = DataSourceProvider.getDataSource();
    @Override
    public Complement save(Complement c) throws Exception {
       String sql = "INSERT INTO complement (nom, prix, image, typecomplement, image_public_id, description, created_at) " +
             "VALUES (?, ?, ?, ?::complement_type, ?, ?, ?) RETURNING id";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setBigDecimal(2, c.getPrix());
            ps.setString(3, c.getImage());
            ps.setString(4, c.getTypeComplement() == null ? null : c.getTypeComplement().name());
            ps.setString(5, c.getImagePublicId());
            ps.setString(6, c.getDescription());
            ps.setTimestamp(7, Timestamp.from(c.getCreatedAt()));
            if (c.getCreatedAt() != null){
                ps.setTimestamp(7, Timestamp.from(c.getCreatedAt()));
            } 
            else{
                ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            } 

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    c.setId(id);
                }
            }
        }
        if (c.getTypeComplement() == ComplementType.FRITE) {
            if (c instanceof Frite) {
                insertFrite((Frite) c);
            } else {
                Frite f = new Frite(c.getId(), c.getNom(), c.getPrix(), c.getImage(),
                                    c.getTypeComplement(), c.getCreatedAt(),
                                    c.getImagePublicId(), c.getDescription(), null);
                insertFrite(f);
            }
        } else if (c.getTypeComplement() == ComplementType.BOISSON) {
            if (c instanceof Boisson) {
                insertBoisson((Boisson)c);
            } else {
                Boisson b = new Boisson(c.getId(), c.getNom(), c.getPrix(), c.getImage(),
                                        c.getTypeComplement(), c.getCreatedAt(),
                                        c.getImagePublicId(), c.getDescription(),null);
                insertBoisson(b);
            }
        }

        return c;
    }

    private void insertFrite(Frite f) throws SQLException {
        String sql = "INSERT INTO frite (id, taille) VALUES (?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, f.getId());
            ps.setString(2, f.getTaille());
            ps.executeUpdate();
        }
    }

    private void insertBoisson(Boisson b) throws SQLException {
        String sql = "INSERT INTO boisson (id, volume) VALUES (?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, b.getId());
            ps.setString(2, b.getVolume());
            ps.executeUpdate();
        }
    }

    @Override
    public Complement update(Complement c) throws Exception {
        String sql = "UPDATE complement SET nom = ?, prix = ?, image = ?,typecomplement = ?, image_public_id = ?,  description = ? WHERE id = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNom());
            ps.setBigDecimal(2, c.getPrix());
            ps.setString(3, c.getImage());
            ps.setString(4, c.getImagePublicId());
            ps.setString(5, c.getTypeComplement() == null ? null : c.getTypeComplement().name());
            ps.setString(6, c.getDescription());
            ps.setLong(7, c.getId());
            ps.executeUpdate();
        }
        return c;
    }

    @Override
    public Complement findById(Long id) throws Exception {
        String sql = "SELECT id, nom, prix, image,typecomplement, image_public_id, description, created_at FROM complement WHERE id = ?";
        try (Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Complement c = mapRow(rs);
                    if (c.getTypeComplement() == ComplementType.FRITE) {
                        loadFriteFields(c);
                    } else if (c.getTypeComplement() == ComplementType.BOISSON) {
                        loadBoissonFields(c);
                    }
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public List<Complement> findAll() throws Exception {
        List<Complement> list = new ArrayList<>();
        String sql = "SELECT id, nom, prix, image,typecomplement, image_public_id, description, created_at FROM complement ORDER BY id";
        try (Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Complement c = mapRow(rs);
                if (c.getTypeComplement() == ComplementType.FRITE) loadFriteFields(c);
                else if (c.getTypeComplement() == ComplementType.BOISSON) loadBoissonFields(c);
                list.add(c);
            }
        }
        return list;
    }

    private Complement mapRow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String nom = rs.getString("nom");
        java.math.BigDecimal prix = rs.getBigDecimal("prix");
        String image = rs.getString("image");
        String typeStr = rs.getString("typecomplement");
        String publicId = rs.getString("image_public_id");
        String description = rs.getString("description");
        Timestamp ts = rs.getTimestamp("created_at");
        java.time.Instant createdAt = ts == null ? null : ts.toInstant();

        ComplementType type = typeStr == null ? null : ComplementType.valueOf(typeStr);
        Complement c = new Complement(id, nom, prix, image, type, createdAt, publicId, description);
        return c;
    }

    private void loadFriteFields(Complement c) throws SQLException {
        String sql = "SELECT taille FROM frite WHERE id = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, c.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String taille = rs.getString("taille");
                    Frite f = new Frite(c.getId(), c.getNom(), c.getPrix(), c.getImage(), c.getTypeComplement(),
                            c.getCreatedAt(), c.getImagePublicId(), c.getDescription(), taille);
                }
            }
        }
    }

    private void loadBoissonFields(Complement c) throws SQLException {
        String sql = "SELECT volume FROM boisson WHERE id = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, c.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String volume = rs.getString("volume");
                    Boisson b = new Boisson(c.getId(), c.getNom(), c.getPrix(), c.getImage(), c.getTypeComplement(),
                            c.getCreatedAt(), c.getImagePublicId(), c.getDescription(), volume);
                }
            }
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        String sql = "DELETE FROM complement WHERE id = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
