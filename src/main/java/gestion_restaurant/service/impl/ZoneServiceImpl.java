package gestion_restaurant.service.impl;

import gestion_restaurant.entity.Zone;
import gestion_restaurant.repository.ZoneRepository;
import gestion_restaurant.service.ZoneService;

import java.util.List;

public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository repo;

    public ZoneServiceImpl(ZoneRepository repo) {
        this.repo = repo;
    }

    @Override
    public Zone create(Zone zone) throws Exception {
        if (zone.getTarif() == null)
            throw new IllegalArgumentException("Le tarif est obligatoire !");
        return repo.save(zone);
    }

    @Override
    public Zone findById(Integer id) throws Exception {
        return repo.findById(id);
    }

    @Override
    public List<Zone> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public Zone update(Integer id, Zone zone) throws Exception {
        Zone existing = repo.findById(id);

        if (existing == null)
            throw new IllegalArgumentException("Zone introuvable id=" + id);

        existing.setTarif(zone.getTarif());
        return repo.save(existing);
    }

    @Override
    public void delete(Integer id) throws Exception {
        if (!repo.delete(id))
            throw new IllegalArgumentException("Impossible de supprimer la zone id=" + id);
    }
}
