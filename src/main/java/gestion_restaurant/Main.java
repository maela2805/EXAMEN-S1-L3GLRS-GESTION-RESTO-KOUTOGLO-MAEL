package gestion_restaurant;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.repository.ZoneRepository;
import gestion_restaurant.repository.QuartierRepository;
import gestion_restaurant.repository.impl.ZoneRepositoryImpl;
import gestion_restaurant.repository.impl.QuartierRepositoryImpl;
import gestion_restaurant.service.ZoneService;
import gestion_restaurant.service.QuartierService;
import gestion_restaurant.service.impl.ZoneServiceImpl;
import gestion_restaurant.service.impl.QuartierServiceImpl;
import gestion_restaurant.view.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
        ZoneRepository zoneRepo = new ZoneRepositoryImpl();
        QuartierRepository quartierRepo = new QuartierRepositoryImpl();
        ZoneService zoneService = new ZoneServiceImpl(zoneRepo);
        QuartierService quartierService = new QuartierServiceImpl(quartierRepo, zoneRepo);
        ConsoleMenu menu = new ConsoleMenu(zoneService, quartierService);
        try {
            menu.start();
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            DataSourceProvider.close();
        }
    }
}
