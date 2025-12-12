package gestion_restaurant;

import gestion_restaurant.cloud.CloudinaryService;
import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.repository.BoissonRepository;
import gestion_restaurant.repository.ComplementRepository;
import gestion_restaurant.repository.FriteRepository;
import gestion_restaurant.repository.LivreurRepository;
import gestion_restaurant.repository.MenuBurgerRepository;
import gestion_restaurant.repository.MenuComplementRepository;
import gestion_restaurant.repository.MenuRepository;
import gestion_restaurant.repository.ProductRepository;
import gestion_restaurant.repository.QuartierRepository;
import gestion_restaurant.repository.ZoneRepository;
import gestion_restaurant.repository.impl.BoissonRepositoryImpl;
import gestion_restaurant.repository.impl.ComplementRepositoryImpl;
import gestion_restaurant.repository.impl.FriteRepositoryImpl;
import gestion_restaurant.repository.impl.LivreurRepositoryImpl;
import gestion_restaurant.repository.impl.MenuBurgerRepositoryImpl;
import gestion_restaurant.repository.impl.MenuComplementRepositoryImpl;
import gestion_restaurant.repository.impl.MenuRepositoryImpl;
import gestion_restaurant.repository.impl.ProductRepositoryImpl;
import gestion_restaurant.repository.impl.QuartierRepositoryImpl;
import gestion_restaurant.repository.UserRepository;
import gestion_restaurant.repository.impl.UserRepositoryImpl;
import gestion_restaurant.repository.impl.ZoneRepositoryImpl;
import gestion_restaurant.service.BurgerService;
import gestion_restaurant.service.ComplementService;
import gestion_restaurant.service.LivreurService;
import gestion_restaurant.service.MenuService;
import gestion_restaurant.service.QuartierService;
import gestion_restaurant.service.ZoneService;
import gestion_restaurant.service.impl.BurgerServiceImpl;
import gestion_restaurant.service.impl.ComplementServiceImpl;
import gestion_restaurant.service.impl.LivreurServiceImpl;
import gestion_restaurant.service.impl.MenuServiceImpl;
import gestion_restaurant.service.impl.QuartierServiceImpl;
import gestion_restaurant.service.impl.ZoneServiceImpl;
import gestion_restaurant.view.ComplementView;
import gestion_restaurant.view.LivreurView;
import gestion_restaurant.view.MenuView;
import gestion_restaurant.view.MyMenu;
import gestion_restaurant.view.BurgerView;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ComplementRepository complementRepo = new ComplementRepositoryImpl();
        CloudinaryService cloudinary = new CloudinaryService();
        FriteRepository friterepo = new FriteRepositoryImpl();
        BoissonRepository boissonRepository = new BoissonRepositoryImpl();
        ComplementService complementService = new ComplementServiceImpl(complementRepo,friterepo,boissonRepository, cloudinary);
        ZoneRepository zoneRepository = new ZoneRepositoryImpl() ;
        QuartierRepository quartierRepository = new QuartierRepositoryImpl();
        QuartierService quartierService = new QuartierServiceImpl(quartierRepository,zoneRepository);
        ZoneService zoneService = new ZoneServiceImpl(zoneRepository);
        MyMenu zoneQuartierMenu= new MyMenu( zoneService,quartierService);
        ProductRepository productRepository = new ProductRepositoryImpl();
        BurgerService burgerService = new BurgerServiceImpl(productRepository,cloudinary);
        BurgerView burgerView = new BurgerView(burgerService);
        ComplementView complementView = new ComplementView(complementService);
        MenuBurgerRepository menuBurgerRepo= new MenuBurgerRepositoryImpl();
        MenuComplementRepository menuComplementRepo = new MenuComplementRepositoryImpl();
        MenuRepository menuRepo = new MenuRepositoryImpl(productRepository, menuBurgerRepo,menuComplementRepo);
        MenuService menuService = new MenuServiceImpl(menuRepo, productRepository, menuBurgerRepo, menuComplementRepo, cloudinary);
        MenuView menuView = new MenuView(menuService);
        UserRepository userRepo = new UserRepositoryImpl();
        LivreurRepository livreurRepo = new LivreurRepositoryImpl();
        LivreurService livreurService = new LivreurServiceImpl(livreurRepo,userRepo);
        LivreurView livreurView = new LivreurView(livreurService);


        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("\n=== Gestion Restaurant - Menu principal ===");
                System.out.println("1) Gérer Zone & Quartier");
                System.out.println("2) Gérer Burgers");
                System.out.println("3) Gérer Compléments");
                System.out.println("4) Gérer Menu");
                System.out.println("5) Gérer Livreur");
                System.out.println("0) Quitter");
                System.out.print("Choix: ");
                String c = sc.nextLine().trim();
                switch (c) {
                    case "1":
                        
                        if (zoneQuartierMenu != null) {
                            zoneQuartierMenu.start();
                        } else {
                            System.out.println("Zone/Quartier non initialisé.");
                        }
                    
                        break;
                    case "2":
                        if (burgerView != null){
                            burgerView.start();
                        }
                        else {
                            System.out.println("Gestion Burgers non initialisée.");
                        }
                        break;
                    case "3":
                        complementView.start();
                        break;
                    case "4":
                        menuView.start();
                        break;
                    case "5":
                        livreurView.start();
                        break;
                    case "0":
                        exit = true; break;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            DataSourceProvider.close();
            sc.close();
        }
        System.out.println("Au revoir !");
    }
}

