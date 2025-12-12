package gestion_restaurant;

import gestion_restaurant.db.DataSourceProvider;
import gestion_restaurant.repository.ComplementRepository;
import gestion_restaurant.repository.impl.ComplementRepositoryImpl;
import gestion_restaurant.service.ComplementService;
import gestion_restaurant.service.impl.ComplementServiceImpl;
import gestion_restaurant.view.ComplementView;
import gestion_restaurant.view.MyMenu; 
import gestion_restaurant.view.BurgerView;   // si tu as déjà cette vue

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ComplementRepository complementRepo = new ComplementRepositoryImpl();
        ComplementService complementService = new ComplementServiceImpl(complementRepo);
        MyMenu zoneQuartierMenu= null; // remplacer par ton constructeur existant
        BurgerView burgerView = null; // si tu as
        ComplementView complementView = new ComplementView(complementService);

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("\n=== Gestion Restaurant - Menu principal ===");
                System.out.println("1) Gérer Zone & Quartier");
                System.out.println("2) Gérer Burgers");
                System.out.println("3) Gérer Compléments");
                System.out.println("0) Quitter");
                System.out.print("Choix: ");
                String c = sc.nextLine().trim();
                switch (c) {
                    case "1":
                        if (zoneQuartierMenu != null) zoneQuartierMenu.start();
                        else System.out.println("Zone/Quartier non initialisé.");
                        break;
                    case "2":
                        if (burgerView != null) burgerView.start();
                        else System.out.println("Gestion Burgers non initialisée.");
                        break;
                    case "3":
                        complementView.start();
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

