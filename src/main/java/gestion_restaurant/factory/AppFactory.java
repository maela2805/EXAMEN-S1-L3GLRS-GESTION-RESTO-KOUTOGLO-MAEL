package gestion_restaurant.factory;

import gestion_restaurant.db.DataSourceProvider;
import java.util.Scanner;

public class AppFactory {

    private final ViewFactory views = new ViewFactory();
    private final Scanner sc = new Scanner(System.in);

    public void run() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Gestion Restaurant - Menu principal ===");
            System.out.println("1) Gérer Zone & Quartier");
            System.out.println("2) Gérer Burgers");
            System.out.println("3) Gérer Compléments");
            System.out.println("4) Gérer Menu");
            System.out.println("5) Gérer Livreurs");
            System.out.println("0) Quitter");
            System.out.print("Choix: ");

            String c = sc.nextLine().trim();

            try {
                switch (c) {
                    case "1": views.zoneQuartierMenu().start(); break;
                    case "2": views.burgerView().start(); break;
                    case "3": views.complementView().start(); break;
                    case "4": views.menuView().start(); break;
                    case "5": views.livreurView().start(); break;
                    case "0": exit = true; break;
                    default: System.out.println("Choix invalide");
                }
            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        }

        DataSourceProvider.close();
        System.out.println("Au revoir !");
    }
}
