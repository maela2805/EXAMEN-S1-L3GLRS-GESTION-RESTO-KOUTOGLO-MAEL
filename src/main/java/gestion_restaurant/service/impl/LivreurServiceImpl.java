package gestion_restaurant.service.impl;

import java.util.List;

import gestion_restaurant.entity.Livreur;
import gestion_restaurant.entity.RoleType;
import gestion_restaurant.entity.User;
import gestion_restaurant.repository.LivreurRepository;
import gestion_restaurant.repository.UserRepository;
import gestion_restaurant.service.LivreurService;
import gestion_restaurant.util.Validator;

public class LivreurServiceImpl implements LivreurService {

    private final LivreurRepository repo;
    private final UserRepository userRepo;

    public LivreurServiceImpl(LivreurRepository repo,UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public Livreur create(Livreur livreur) throws Exception {

        if (livreur == null)
            throw new IllegalArgumentException("livreur null");
        if (!Validator.isValidTelephone(livreur.getTelephone())) {
            throw new IllegalArgumentException(
                "Numéro invalide ! Il doit faire 9 chiffres et commencer par 77 ou 78."
            );
        }
        livreur.setRole(RoleType.LIVREUR);
        User savedUser = userRepo.save((User) livreur);

        if (savedUser.getId() == null){
            throw new IllegalStateException("Impossible d'insérer l'utilisateur");
        }
        livreur.setId(savedUser.getId());
        repo.save(livreur);

        return livreur;
    }


    @Override
    public Livreur update(Livreur l) throws Exception {
        return repo.update(l);
    }

    @Override
    public Livreur findById(Long id) throws Exception {
        return repo.findById(id);
    }

    @Override
    public List<Livreur> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) throws Exception {
        repo.delete(id);
    }
}
