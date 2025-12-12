package gestion_restaurant.service.impl;

import gestion_restaurant.entity.Quartier;
import gestion_restaurant.entity.Zone;
import gestion_restaurant.repository.QuartierRepository;
import gestion_restaurant.repository.ZoneRepository;
import gestion_restaurant.service.QuartierService;

import java.util.List;

public class QuartierServiceImpl implements QuartierService {

    private final QuartierRepository quartierRepo;
    private final ZoneRepository zoneRepo;

    public QuartierServiceImpl(QuartierRepository quartierRepo, ZoneRepository zoneRepo) {
        this.quartierRepo = quartierRepo;
        this.zoneRepo = zoneRepo;
    }

    @Override
    public Quartier create(Quartier quartier) throws Exception {

        if (quartier.getZone() != null && quartier.getZone().getId() != null) {
            Zone z = zoneRepo.findById(quartier.getZone().getId());
            if (z == null)
                throw new IllegalArgumentException("Zone introuvable id=" + quartier.getZone().getId());

            quartier.setZone(z);
        } else {
            quartier.setZone(null);
        }

        return quartierRepo.save(quartier);
    }

    @Override
    public Quartier findById(Integer id) throws Exception {
        return quartierRepo.findById(id);
    }

    @Override
    public List<Quartier> findAll() throws Exception {
        return quartierRepo.findAll();
    }

    @Override
    public List<Quartier> findByZoneId(Integer zoneId) throws Exception {
        return quartierRepo.findByZoneId(zoneId);
    }

    @Override
    public Quartier update(Integer id, Quartier q) throws Exception {

        Quartier existing = quartierRepo.findById(id);

        if (existing == null)
            throw new IllegalArgumentException("Quartier introuvable id=" + id);

        existing.setLibelle(q.getLibelle());

        if (q.getZone() != null && q.getZone().getId() != null) {
            Zone newZone = zoneRepo.findById(q.getZone().getId());
            if (newZone == null)
                throw new IllegalArgumentException("Zone introuvable id=" + q.getZone().getId());
            existing.setZone(newZone);
        } else {
            existing.setZone(null);
        }

        return quartierRepo.save(existing);
    }

    @Override
    public void delete(Integer id) throws Exception {
        if (!quartierRepo.delete(id))
            throw new IllegalArgumentException("Impossible de supprimer le quartier id=" + id);
    }
}
