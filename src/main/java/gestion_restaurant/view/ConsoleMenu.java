package gestion_restaurant.view;

import gestion_restaurant.entity.Quartier;
import gestion_restaurant.entity.Zone;
import gestion_restaurant.service.QuartierService;
import gestion_restaurant.service.ZoneService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final ZoneService zoneService;
    private final QuartierService quartierService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(ZoneService zoneService, QuartierService quartierService) {
        this.zoneService = zoneService;
        this.quartierService = quartierService;
    }

    public void start() {
        System.out.println("=== Gestion Restaurant===");
        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createZone(); break;
                    case "2": listZones(); break;
                    case "3": updateZone(); break;
                    case "4": deleteZone(); break;
                    case "5": createQuartier(); break;
                    case "6": listQuartiers(); break;
                    case "7": listQuartiersByZone(); break;
                    case "8": updateQuartier(); break;
                    case "9": deleteQuartier(); break;
                    case "0": exit = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        }
        System.out.println("Au revoir !");
    }

    private void printMenu() {
        System.out.println("\n1) Créer Zone");
        System.out.println("2) Lister Zones");
        System.out.println("3) Mettre à jour Zone");
        System.out.println("4) Supprimer Zone");
        System.out.println("5) Créer Quartier");
        System.out.println("6) Lister Tous les Quartiers");
        System.out.println("7) Lister Quartiers par Zone");
        System.out.println("8) Mettre à jour Quartier");
        System.out.println("9) Supprimer Quartier");
        System.out.println("0) Quitter");
        System.out.print("Choix: ");
    }

    private void createZone() throws Exception {
        System.out.print("Tarif (ex: 1500.50): ");
        String tarifStr = scanner.nextLine().trim();
        BigDecimal tarif;
        try {
            tarif = new BigDecimal(tarifStr);
        } catch (NumberFormatException ex) {
            System.out.println("Tarif invalide.");
            return;
        }
        Zone zone = new Zone();
        zone.setTarif(tarif);
        Zone saved = zoneService.create(zone);
        System.out.println("Zone créée : id=" + saved.getId() + " tarif=" + saved.getTarif());
    }

    private void listZones() throws Exception {
        List<Zone> zones = zoneService.findAll();
        System.out.println("Zones:");
        if (zones.isEmpty()) {
            System.out.println(" (aucune)");
            return;
        }
        zones.forEach(z -> System.out.println(" - id=" + z.getId() + " tarif=" + z.getTarif()));
    }

    private void updateZone() throws Exception {
        System.out.print("Id zone à modifier: ");
        String idStr = scanner.nextLine().trim();
        Integer id;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException ex) {
            System.out.println("Id invalide.");
            return;
        }
        Zone z = zoneService.findById(id);
        if (z == null) { System.out.println("Zone introuvable."); return; }
        System.out.println("Tarif actuel: " + z.getTarif());
        System.out.print("Nouveau tarif: ");
        String t = scanner.nextLine().trim();
        try {
            z.setTarif(new BigDecimal(t));
        } catch (NumberFormatException ex) {
            System.out.println("Tarif invalide.");
            return;
        }
        Zone updated = zoneService.update(id, z);
        System.out.println("Zone mise à jour: " + updated.getId());
    }

    private void deleteZone() throws Exception {
        System.out.print("Id zone à supprimer: ");
        String idStr = scanner.nextLine().trim();
        Integer id;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException ex) {
            System.out.println("Id invalide.");
            return;
        }
        zoneService.delete(id);
        System.out.println("Zone supprimée.");
    }

    private void createQuartier() throws Exception {
        System.out.print("Libelle du quartier: ");
        String lib = scanner.nextLine().trim();
        if (lib.isEmpty()) {
            System.out.println("Libellé requis.");
            return;
        }

        System.out.print("Zone id pour ce quartier (laisser vide si none): ");
        String zoneIdStr = scanner.nextLine().trim();
        Quartier q = new Quartier();
        q.setLibelle(lib);
        if (!zoneIdStr.isEmpty()) {
            try {
                Integer zoneId = Integer.valueOf(zoneIdStr);
                Zone z = new Zone();
                z.setId(zoneId);
                q.setZone(z);
            } catch (NumberFormatException ex) {
                System.out.println("Zone id invalide.");
                return;
            }
        }

        Quartier saved = quartierService.create(q);
        System.out.println("Quartier créé id=" + saved.getId() + " libelle=" + saved.getLibelle());
    }

    private void listQuartiers() throws Exception {
        List<Quartier> list = quartierService.findAll();
        System.out.println("Quartiers:");
        if (list.isEmpty()) {
            System.out.println(" (aucun)");
            return;
        }
        list.forEach(q -> System.out.println(" - id=" + q.getId() + " libelle=" + q.getLibelle() +
                (q.getZone() != null ? (" zoneId=" + q.getZone().getId()) : "")));
    }

    private void listQuartiersByZone() throws Exception {
        System.out.print("Zone id: ");
        String zid = scanner.nextLine().trim();
        Integer zoneId;
        try {
            zoneId = Integer.valueOf(zid);
        } catch (NumberFormatException ex) {
            System.out.println("Id invalide.");
            return;
        }
        List<Quartier> list = quartierService.findByZoneId(zoneId);
        System.out.println("Quartiers pour zone " + zoneId + ":");
        if (list.isEmpty()) System.out.println(" (aucun)");
        list.forEach(q -> System.out.println(" - id=" + q.getId() + " libelle=" + q.getLibelle()));
    }

    private void updateQuartier() throws Exception {
        System.out.print("Id quartier à modifier: ");
        String idStr = scanner.nextLine().trim();
        Integer id;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException ex) {
            System.out.println("Id invalide.");
            return;
        }
        Quartier q = quartierService.findById(id);
        if (q == null) { System.out.println("Quartier introuvable."); return; }
        System.out.println("Libelle actuel: " + q.getLibelle());
        System.out.print("Nouveau libelle (laisser vide pour ne pas changer): ");
        String lib = scanner.nextLine().trim();
        if (!lib.isEmpty()) q.setLibelle(lib);
        System.out.print("Changer de zone ? (oui/non): ");
        String changeZone = scanner.nextLine().trim();
        if ("oui".equalsIgnoreCase(changeZone)) {
            System.out.print("Nouvelle zone id (laisser vide pour null): ");
            String newZoneIdStr = scanner.nextLine().trim();
            if (newZoneIdStr.isEmpty()) q.setZone(null);
            else {
                try {
                    Integer newZoneId = Integer.valueOf(newZoneIdStr);
                    Zone newZone = new Zone();
                    newZone.setId(newZoneId);
                    q.setZone(newZone);
                } catch (NumberFormatException ex) {
                    System.out.println("Id invalide.");
                    return;
                }
            }
        }
        Quartier updated = quartierService.update(id, q);
        System.out.println("Quartier mis à jour id=" + updated.getId());
    }

    private void deleteQuartier() throws Exception {
        System.out.print("Id quartier à supprimer: ");
        String idStr = scanner.nextLine().trim();
        Integer id;
        try {
            id = Integer.valueOf(idStr);
        } catch (NumberFormatException ex) {
            System.out.println("Id invalide.");
            return;
        }
        quartierService.delete(id);
        System.out.println("Quartier supprimé.");
    }
}
