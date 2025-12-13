package gestion_restaurant.factory;

import gestion_restaurant.cloud.CloudinaryService;
import gestion_restaurant.service.*;
import gestion_restaurant.service.impl.*;

public class ServiceFactory {

    private final RepositoryFactory repo = new RepositoryFactory();
    private final CloudinaryService cloud = new CloudinaryService();

    private final ComplementService complementService =
            new ComplementServiceImpl(repo.complement(), repo.frite(), repo.boisson(), cloud);
    private final ZoneService zoneService = new ZoneServiceImpl(repo.zone());
    private final QuartierService quartierService = new QuartierServiceImpl(repo.quartier(), repo.zone());
    private final BurgerService burgerService = new BurgerServiceImpl(repo.product(), cloud);
    private final MenuService menuService =
            new MenuServiceImpl(repo.menu(), repo.product(), repo.menuBurger(), repo.menuComplement(), cloud);
    private final LivreurService livreurService =
            new LivreurServiceImpl(repo.livreur(), repo.user());
    public ComplementService complement() { return complementService; }
    public ZoneService zone() { return zoneService; }
    public QuartierService quartier() { return quartierService; }
    public BurgerService burger() { return burgerService; }
    public MenuService menu() { return menuService; }
    public LivreurService livreur() { return livreurService; }
}
