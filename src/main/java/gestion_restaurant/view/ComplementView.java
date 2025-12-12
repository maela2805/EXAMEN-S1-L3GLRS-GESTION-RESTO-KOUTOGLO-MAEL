package gestion_restaurant.view;

import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.service.ComplementService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ComplementView {

    private final ComplementService service;
    private final Scanner scanner = new Scanner(System.in);

    public ComplementView(ComplementService service) {
        this.service = service;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createComplement(); break;
                    case "2": listComplements(); break;
                    case "3": listByType(); break;
                    case "4": updateComplement(); break;
                    case "5": deleteComplement(); break;
                    case "0": exit = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Gestion Compléments ---");
        System.out.println("1) Créer complément");
        System.out.println("2) Lister tous les compléments");
        System.out.println("3) Lister par type (FRITE/BOISSON)");
        System.out.println("4) Mettre à jour complément");
        System.out.println("5) Supprimer complément");
        System.out.println("0) Retour");
        System.out.print("Choix: ");
    }

    private void createComplement() throws Exception {
        System.out.print("Nom: ");
        String nom = scanner.nextLine().trim();
        System.out.print("Prix (ex: 500.00): ");
        BigDecimal prix;
        try {
            prix = new BigDecimal(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Prix invalide."); return;
        }

        System.out.print("Type (FRITE/BOISSON): ");
        String t = scanner.nextLine().trim().toUpperCase();
        ComplementType type;
        try { type = ComplementType.valueOf(t); } catch (Exception ex) { System.out.println("Type invalide."); return; }

        System.out.print("Image URL (laisser vide si none): ");
        String image = scanner.nextLine().trim();
        System.out.print("Description (laisser vide si none): ");
        String desc = scanner.nextLine().trim();

        Complement c = new Complement();
        c.setNom(nom);
        c.setPrix(prix);
        c.setTypeComplement(type);
        c.setImage(image.isEmpty() ? null : image);
        c.setDescription(desc.isEmpty() ? null : desc);

        Complement saved = service.create(c);
        System.out.println("Complément créé id=" + saved.getId());
    }

    private void listComplements() throws Exception {
        List<Complement> list = service.findAll();
        System.out.println("Compléments:");
        if (list.isEmpty()) { System.out.println(" (aucun)"); return; }
        list.forEach(c -> System.out.println(" - id=" + c.getId() + " nom=" + c.getNom() + " type=" + c.getTypeComplement() + " prix=" + c.getPrix()));
    }

    private void listByType() throws Exception {
        System.out.print("Type (FRITE/BOISSON): ");
        String t = scanner.nextLine().trim().toUpperCase();
        ComplementType type;
        try { type = ComplementType.valueOf(t); } catch (Exception ex) { System.out.println("Type invalide."); return; }
        List<Complement> list = service.findByType(type);
        System.out.println("Compléments de type " + type + ":");
        if (list.isEmpty()) System.out.println(" (aucun)");
        list.forEach(c -> System.out.println(" - id=" + c.getId() + " nom=" + c.getNom() + " prix=" + c.getPrix()));
    }

    private void updateComplement() throws Exception {
        System.out.print("Id du complément à modifier: ");
        Long id;
        try { id = Long.valueOf(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Id invalide."); return; }
        Complement existing = service.findById(id);
        if (existing == null) { System.out.println("Complément introuvable."); return; }

        System.out.println("Nom actuel: " + existing.getNom());
        System.out.print("Nouveau nom (laisser vide pour ne pas changer): ");
        String nom = scanner.nextLine().trim();
        if (!nom.isEmpty()) existing.setNom(nom);

        System.out.println("Prix actuel: " + existing.getPrix());
        System.out.print("Nouveau prix (laisser vide pour ne pas changer): ");
        String prixStr = scanner.nextLine().trim();
        if (!prixStr.isEmpty()) existing.setPrix(new BigDecimal(prixStr));

        System.out.println("Type actuel: " + existing.getTypeComplement());
        System.out.print("Nouveau type (FRITE/BOISSON) (laisser vide pour ne pas changer): ");
        String typeStr = scanner.nextLine().trim();
        if (!typeStr.isEmpty()) existing.setTypeComplement(ComplementType.valueOf(typeStr.toUpperCase()));

        System.out.print("Nouvelle image URL (laisser vide pour ne pas changer): ");
        String img = scanner.nextLine().trim();
        if (!img.isEmpty()) existing.setImage(img);

        System.out.print("Nouvelle description (laisser vide pour ne pas changer): ");
        String desc = scanner.nextLine().trim();
        if (!desc.isEmpty()) existing.setDescription(desc);

        Complement updated = service.update(id, existing);
        System.out.println("Complément mis à jour id=" + updated.getId());
    }

    private void deleteComplement() throws Exception {
        System.out.print("Id du complément à supprimer: ");
        Long id;
        try { id = Long.valueOf(scanner.nextLine().trim()); } catch (Exception ex) { System.out.println("Id invalide."); return; }
        service.delete(id);
        System.out.println("Complément supprimé.");
    }
}
