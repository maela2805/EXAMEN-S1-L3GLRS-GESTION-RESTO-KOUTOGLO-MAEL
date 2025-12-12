package gestion_restaurant.service.impl;

import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.repository.ComplementRepository;
import gestion_restaurant.service.ComplementService;

import java.util.List;

public class ComplementServiceImpl implements ComplementService {

    private final ComplementRepository repo;

    public ComplementServiceImpl(ComplementRepository repo) {
        this.repo = repo;
    }

    @Override
    public Complement create(Complement c) throws Exception {
        return repo.save(c);
    }

    @Override
    public Complement update(Long id, Complement c) throws Exception {
        c.setId(id);
        return repo.update(c);
    }

    @Override
    public Complement findById(Long id) throws Exception {
        return repo.findById(id);
    }

    @Override
    public List<Complement> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public List<Complement> findByType(ComplementType type) throws Exception {
        return repo.findByType(type);
    }

    @Override
    public void delete(Long id) throws Exception {
        repo.delete(id);
    }
}
