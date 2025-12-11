package gestion_restaurant;

import gestion_restaurant.cloud.CloudinaryService;
import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.repository.ProductRepository;
import gestion_restaurant.repository.QuartierRepository;
import gestion_restaurant.repository.ZoneRepository;
import gestion_restaurant.repository.impl.ProductRepositoryImpl;
import gestion_restaurant.repository.impl.QuartierRepositoryImpl;
import gestion_restaurant.repository.impl.ZoneRepositoryImpl;
import gestion_restaurant.service.BurgerService;
import gestion_restaurant.service.QuartierService;
import gestion_restaurant.service.ZoneService;
import gestion_restaurant.service.impl.BurgerServiceImpl;
import gestion_restaurant.service.impl.QuartierServiceImpl;
import gestion_restaurant.service.impl.ZoneServiceImpl;
import gestion_restaurant.view.BurgerView;
import gestion_restaurant.view.ConsoleMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ZoneRepository zoneRepo = null;
        QuartierRepository quartierRepo = null;
        ZoneService zoneService = null;
        QuartierService quartierService = null;

        ProductRepository productRepo = null;
        BurgerService burgerService = null;
        CloudinaryService cloudinaryService = null;

        try {
            zoneRepo = new ZoneRepositoryImpl();
            quartierRepo = new QuartierRepositoryImpl();
            zoneService = new ZoneServiceImpl(zoneRepo);
            quartierService = new QuartierServiceImpl(quartierRepo, zoneRepo);
            try {
                productRepo = new ProductRepositoryImpl();
                cloudinaryService = new CloudinaryService();
                burgerService = new BurgerServiceImpl(productRepo, cloudinaryService);

                String cloudName = System.getenv("CLOUDINARY_CLOUD_NAME");
                if (cloudName == null || cloudName.isBlank()) {
                    System.out.println("Cloudinary non configuré (CLOUDINARY_* manquantes). L'option Burger sera désactivée.");
                } else {
                    System.out.println("Cloudinary détecté (cloud: " + cloudName + "). Option Burger activée.");
                }
            } catch (Throwable t) {
                System.out.println("Product/Cloudinary non initialisé. L'option Burger sera désactivée.");
                burgerService = null;
            }

            ConsoleMenu zoneMenu = new ConsoleMenu(zoneService, quartierService);
            BurgerView burgerView = (burgerService != null) ? new BurgerView(burgerService) : null;

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== Gestion Restaurant - Menu principal ===");
                System.out.println("1) Gérer Zone & Quartier");
                System.out.println("2) Gérer Burgers");
                System.out.println("0) Quitter");
                System.out.print("Choix: ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        zoneMenu.start();
                        break;
                    case "2":
                        if (burgerView == null) {
                            System.out.println("Fonctionnalité Burger indisponible. Vérifiez la configuration Cloudinary et ProductRepository.");
                        } else {
                            burgerView.start();
                        }
                        break;
                    case "0":
                        exit = true;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            }
            System.out.println("Au revoir !");
        } catch (Exception e) {
            System.err.println("Erreur inattendue au démarrage : " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            try {
                DataSourceProvider.close();
            } catch (Exception ex) {
                System.err.println("Erreur lors de la fermeture du DataSource : " + ex.getMessage());
                ex.printStackTrace(System.err);
            }
        }
    }
}
