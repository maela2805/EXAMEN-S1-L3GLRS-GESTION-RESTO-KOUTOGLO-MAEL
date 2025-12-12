package gestion_restaurant.repository;

import java.util.List;

import gestion_restaurant.entity.User;

public interface UserRepository {
    User save(User u) throws Exception;
    User update(User u) throws Exception;
    User findById(Long id) throws Exception;
    List<User> findAll() throws Exception;
    void delete(Long id) throws Exception;
}
