package gestion_restaurant.view;

import gestion_restaurant.entity.Burger;
import gestion_restaurant.entity.ProductType;
import gestion_restaurant.service.BurgerService;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class BurgerView {

    private final BurgerService burgerService;
    private final Scanner scanner = new Scanner(System.in);

    public BurgerView(BurgerService burgerService) {
        this.burgerService = burgerService;
    }

    public void start() {
        boolean back = false;
        while (!back) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createBurger(); break;
                    case "2": listBurgers(); break;
                    case "3": updateBurgerImage(); break;
                    case "4": deleteBurger(); break;
                    case "0": back = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Gestion Burgers ---");
        System.out.println("1) Créer Burger (avec image)");
        System.out.println("2) Lister Burgers");
        System.out.println("3) Mettre à jour l'image d'un Burger");
        System.out.println("4) Supprimer Burger");
        System.out.println("0) Retour");
        System.out.print("Choix: ");
    }

    private void createBurger() {
        try {
            System.out.print("Nom du burger: ");
            String nom = scanner.nextLine().trim();
            if (nom.isEmpty()) { System.out.println("Nom requis."); return; }

            System.out.print("Prix (ex: 3500.00): ");
            String prixStr = scanner.nextLine().trim();
            java.math.BigDecimal prix;
            try { prix = new java.math.BigDecimal(prixStr); } catch (NumberFormatException e) { System.out.println("Prix invalide."); return; }

            System.out.print("Description (laisser vide si non): ");
            String desc = scanner.nextLine().trim();

            System.out.print("Chemin fichier image (laisser vide si pas d'image): ");
            String path = scanner.nextLine().trim();
            File imgFile = null;
            if (!path.isEmpty()) {
                imgFile = new File(path);
                if (!imgFile.exists() || !imgFile.isFile()) { System.out.println("Fichier introuvable : " + path); return; }
                if (!isAllowedImageExtension(imgFile)) { System.out.println("Format non supporté. Utilise jpg/jpeg/png/webp."); return; }
                long maxBytes = 10L * 1024 * 1024;
                if (imgFile.length() > maxBytes) { System.out.println("Fichier trop volumineux (>10MB)."); return; }
            }

            Burger b = new Burger();
            b.setNom(nom);
            b.setPrix(prix);
            b.setTypeProduit(ProductType.BURGER);
            b.setDescription(desc);

            Burger saved = burgerService.create(b, imgFile);
            System.out.println("Burger créé id=" + saved.getId());
            if (saved.getImage() != null) System.out.println("Image URL: " + saved.getImage());
        } catch (Exception ex) {
            System.err.println("Erreur création burger: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private void listBurgers() {
        try {
            List<Burger> list = burgerService.findAll();
            System.out.println("Burgers:");
            if (list.isEmpty()) { System.out.println(" (aucun)"); return; }
            list.forEach(b -> System.out.println(" - id=" + b.getId() + " nom=" + b.getNom() +
                    " prix=" + b.getPrix() + (b.getImage() != null ? " image=" + b.getImage() : "")));
        } catch (Exception ex) {
            System.err.println("Erreur listage: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private void updateBurgerImage() {
        try {
            System.out.print("Id du burger à modifier: ");
            String idStr = scanner.nextLine().trim();
            Long id;
            try { id = Long.valueOf(idStr); } catch (NumberFormatException ex) { System.out.println("Id invalide."); return; }

            System.out.print("Chemin nouvelle image: ");
            String path = scanner.nextLine().trim();
            if (path.isEmpty()) { System.out.println("Chemin vide."); return; }
            File img = new File(path);
            if (!img.exists() || !img.isFile()) { System.out.println("Fichier introuvable."); return; }
            if (!isAllowedImageExtension(img)) { System.out.println("Format non supporté."); return; }

            Burger updated = burgerService.updateImage(id, img);
            System.out.println("Image mise à jour pour burger id=" + updated.getId());
            if (updated.getImage() != null) System.out.println("Nouvelle image URL: " + updated.getImage());
        } catch (Exception ex) {
            System.err.println("Erreur update image: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private void deleteBurger() {
        try {
            System.out.print("Id du burger à supprimer: ");
            String idStr = scanner.nextLine().trim();
            Long id;
            try { id = Long.valueOf(idStr); } catch (NumberFormatException ex) { System.out.println("Id invalide."); return; }

            burgerService.delete(id);
            System.out.println("Burger supprimé.");
        } catch (Exception ex) {
            System.err.println("Erreur suppression burger: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private boolean isAllowedImageExtension(java.io.File f) {
        String name = f.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".webp");
    }
}
