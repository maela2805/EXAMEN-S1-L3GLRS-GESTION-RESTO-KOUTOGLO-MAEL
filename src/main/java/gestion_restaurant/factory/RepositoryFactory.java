package gestion_restaurant.factory;

import gestion_restaurant.repository.*;
import gestion_restaurant.repository.impl.*;

public class RepositoryFactory {

    private final ComplementRepository complementRepo = new ComplementRepositoryImpl();
    private final FriteRepository friteRepo = new FriteRepositoryImpl();
    private final BoissonRepository boissonRepo = new BoissonRepositoryImpl();
    private final ZoneRepository zoneRepo = new ZoneRepositoryImpl();
    private final QuartierRepository quartierRepo = new QuartierRepositoryImpl();
    private final ProductRepository productRepo = new ProductRepositoryImpl();
    private final MenuBurgerRepository menuBurgerRepo = new MenuBurgerRepositoryImpl();
    private final MenuComplementRepository menuComplementRepo = new MenuComplementRepositoryImpl();
    private final MenuRepository menuRepo = new MenuRepositoryImpl(productRepo, menuBurgerRepo, menuComplementRepo);
    private final UserRepository userRepo = new UserRepositoryImpl();
    private final LivreurRepository livreurRepo = new LivreurRepositoryImpl();
    public ComplementRepository complement() { return complementRepo; }
    public FriteRepository frite() { return friteRepo; }
    public BoissonRepository boisson() { return boissonRepo; }
    public ZoneRepository zone() { return zoneRepo; }
    public QuartierRepository quartier() { return quartierRepo; }
    public ProductRepository product() { return productRepo; }
    public MenuBurgerRepository menuBurger() { return menuBurgerRepo; }
    public MenuComplementRepository menuComplement() { return menuComplementRepo; }
    public MenuRepository menu() { return menuRepo; }
    public UserRepository user() { return userRepo; }
    public LivreurRepository livreur() { return livreurRepo; }
}
