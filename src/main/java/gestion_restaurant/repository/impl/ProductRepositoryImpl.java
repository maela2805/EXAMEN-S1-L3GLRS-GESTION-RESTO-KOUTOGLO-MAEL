package gestion_restaurant.repository.impl;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.entity.Burger;
import gestion_restaurant.entity.Product;
import gestion_restaurant.entity.ProductType;
import gestion_restaurant.repository.ProductRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private final DataSource ds = DataSourceProvider.getDataSource();

    @Override
    public Product save(Product p) throws Exception {
        try (Connection c = ds.getConnection()) {
            c.setAutoCommit(false);
            try {

                String sql = "INSERT INTO product (nom, image, image_public_id, prix, typeproduit, description) " +
                            "VALUES (?, ?, ?, ?, ?::product_type, ?) RETURNING id";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, p.getNom());
                    ps.setString(2, p.getImage());
                    ps.setString(3, p.getImagePublicId());
                    ps.setBigDecimal(4, p.getPrix());
                    ps.setString(5, p.getTypeProduit() == null ? null : p.getTypeProduit().name());
                    ps.setString(6, p.getDescription());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) p.setId(rs.getLong("id"));
                    }
                }
                if (p instanceof Burger) {
                    String sqlBurger = "INSERT INTO burger (id, description) VALUES (?, ?)";
                    try (PreparedStatement ps2 = c.prepareStatement(sqlBurger)) {
                        ps2.setLong(1, p.getId());
                        ps2.setString(2, ((Burger) p).getDescription());
                        ps2.executeUpdate();
                    }
                }

                c.commit();
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
        return p;
    }

    @Override
    public Product update(Product p) throws Exception {
        try (Connection c = ds.getConnection()) {
            c.setAutoCommit(false);
            try {
                String sql = "UPDATE product SET nom = ?, image = ?, image_public_id = ?, prix = ?, typeproduit = ?::product_type, description = ? WHERE id = ?";
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, p.getNom());
                    ps.setString(2, p.getImage());
                    ps.setString(3, p.getImagePublicId());
                    ps.setBigDecimal(4, p.getPrix());
                    ps.setString(5, p.getTypeProduit() == null ? null : p.getTypeProduit().name());
                    ps.setString(6, p.getDescription());
                    ps.setLong(7, p.getId());
                    ps.executeUpdate();
                }
                if (p instanceof Burger) {
                    String check = "SELECT 1 FROM burger WHERE id = ?";
                    boolean exists = false;
                    try (PreparedStatement psChk = c.prepareStatement(check)) {
                        psChk.setLong(1, p.getId());
                        try (ResultSet rs = psChk.executeQuery()) {
                            exists = rs.next();
                        }
                    }

                    if (exists) {
                        String u = "UPDATE burger SET description = ? WHERE id = ?";
                        try (PreparedStatement psu = c.prepareStatement(u)) {
                            psu.setString(1, ((Burger) p).getDescription());
                            psu.setLong(2, p.getId());
                            psu.executeUpdate();
                        }
                    } else {
                        String ins = "INSERT INTO burger (id, description) VALUES (?, ?)";
                        try (PreparedStatement psi = c.prepareStatement(ins)) {
                            psi.setLong(1, p.getId());
                            psi.setString(2, ((Burger) p).getDescription());
                            psi.executeUpdate();
                        }
                    }
                } else {
                    String del = "DELETE FROM burger WHERE id = ?";
                    try (PreparedStatement psd = c.prepareStatement(del)) {
                        psd.setLong(1, p.getId());
                        psd.executeUpdate();
                    }
                }

                c.commit();
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
        return p;
    }

    @Override
    public void delete(Long id) throws Exception {
        try (Connection c = ds.getConnection()) {
            c.setAutoCommit(false);
            try {
                String delBurger = "DELETE FROM burger WHERE id = ?";
                try (PreparedStatement psb = c.prepareStatement(delBurger)) {
                    psb.setLong(1, id);
                    psb.executeUpdate();
                }
                String delProd = "DELETE FROM product WHERE id = ?";
                try (PreparedStatement psp = c.prepareStatement(delProd)) {
                    psp.setLong(1, id);
                    psp.executeUpdate();
                }

                c.commit();
            } catch (Exception ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    @Override
    public Product findById(Long id) throws Exception {
        String sql = "SELECT p.id, p.nom, p.image, p.image_public_id, p.prix, p.typeproduit, p.created_at, p.description, b.description AS burger_description " +
                     "FROM product p LEFT JOIN burger b ON p.id = b.id WHERE p.id = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String typeStr = rs.getString("typeproduit");
                    ProductType type = typeStr == null ? null : ProductType.valueOf(typeStr);
                    if (type == ProductType.BURGER) {
                        Burger b = new Burger();
                        b.setId(rs.getLong("id"));
                        b.setNom(rs.getString("nom"));
                        b.setImage(rs.getString("image"));
                        b.setImagePublicId(rs.getString("image_public_id"));
                        b.setPrix(rs.getBigDecimal("prix"));
                        b.setTypeProduit(type);
                        Timestamp t = rs.getTimestamp("created_at");
                        if (t != null) b.setCreatedAt(t.toInstant());
                        b.setDescription(rs.getString("burger_description"));
                        return b;
                    } else {
                        // generic product
                        Product p = new Product() {};
                        p.setId(rs.getLong("id"));
                        p.setNom(rs.getString("nom"));
                        p.setImage(rs.getString("image"));
                        p.setImagePublicId(rs.getString("image_public_id"));
                        p.setPrix(rs.getBigDecimal("prix"));
                        p.setTypeProduit(type);
                        Timestamp t = rs.getTimestamp("created_at");
                        if (t != null) p.setCreatedAt(t.toInstant());
                        p.setDescription(rs.getString("description"));
                        return p;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Burger> findAllBurgers() throws Exception {
        List<Burger> list = new ArrayList<>();
        String sql = "SELECT p.id, p.nom, p.image, p.image_public_id, p.prix, p.typeproduit, p.created_at, b.description " +
                     "FROM product p JOIN burger b ON p.id = b.id WHERE p.typeproduit = 'BURGER' ORDER BY p.id";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Burger b = new Burger();
                b.setId(rs.getLong("id"));
                b.setNom(rs.getString("nom"));
                b.setImage(rs.getString("image"));
                b.setImagePublicId(rs.getString("image_public_id"));
                b.setPrix(rs.getBigDecimal("prix"));
                b.setTypeProduit(ProductType.BURGER);
                Timestamp t = rs.getTimestamp("created_at");
                if (t != null) b.setCreatedAt(t.toInstant());
                b.setDescription(rs.getString("description"));
                list.add(b);
            }
        }
        return list;
    }
}
