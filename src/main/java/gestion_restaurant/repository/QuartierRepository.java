package gestion_restaurant.repository;

import gestion_restaurant.entity.Quartier;

import java.sql.SQLException;
import java.util.List;

public interface QuartierRepository {
    Quartier save(Quartier quartier) throws SQLException;
    List<Quartier> findAll() throws SQLException;
    List<Quartier> findByZoneId(int zoneId) throws SQLException;
    Quartier findById(Integer id) throws SQLException;
    boolean delete(Integer id) throws SQLException;
}
