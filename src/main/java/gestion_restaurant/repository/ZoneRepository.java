package gestion_restaurant.repository;

import gestion_restaurant.entity.Zone;

import java.sql.SQLException;
import java.util.List;

public interface ZoneRepository {
    Zone save(Zone zone) throws SQLException;
    List<Zone> findAll() throws SQLException;
    Zone findById(Integer id) throws SQLException;
    boolean delete(Integer id) throws SQLException;
}
