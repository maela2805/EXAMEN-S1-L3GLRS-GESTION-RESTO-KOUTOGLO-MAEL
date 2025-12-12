package gestion_restaurant.view;

import gestion_restaurant.entity.Boisson;
import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.entity.Frite;
import gestion_restaurant.service.ComplementService;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                e.printStackTrace(System.err);
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
        if (nom.isEmpty()) { 
            System.out.println("Nom requis.");
             return; 
        }

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
        try {
            type = ComplementType.valueOf(t);
        } catch (Exception ex) {
            System.out.println("Type invalide."); return;
        }

        String taille = null;
        String volume = null;
        if (type == ComplementType.FRITE) {
            System.out.print("Taille (ex: petite/moyenne/grande) (laisser vide si none): ");
            taille = scanner.nextLine().trim();
            if (taille.isEmpty()) taille = null;
        } else if (type == ComplementType.BOISSON) {
            System.out.print("Volume (ex: 500ml) (laisser vide si none): ");
            volume = scanner.nextLine().trim();
            if (volume.isEmpty()) volume = null;
        }

        System.out.print("Description (laisser vide si none): ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) desc = null;

        System.out.print("Chemin fichier image (laisser vide si pas d'image): ");
        String path = scanner.nextLine().trim();
        File imgFile = null;
        if (!path.isEmpty()) {
            imgFile = new File(path);
            if (!imgFile.exists() || !imgFile.isFile()) {
                System.out.println("Fichier introuvable : " + path);
                return;
            }
            if (!isAllowedImageExtension(imgFile)) {
                System.out.println("Format non supporté. Utilise jpg/jpeg/png/webp.");
                return;
            }
            long maxBytes = 10L * 1024 * 1024;
            if (imgFile.length() > maxBytes) {
                System.out.println("Fichier trop volumineux (>10MB).");
                return;
            }
        }

        Complement c;
        if (type == ComplementType.FRITE) {
            Frite f = new Frite();
            f.setNom(nom);
            f.setPrix(prix);
            f.setTypeComplement(type);
            f.setDescription(desc);
            f.setTaille(taille);
            c = f;
        } else if (type == ComplementType.BOISSON) {
            Boisson b = new Boisson();
            b.setNom(nom);
            b.setPrix(prix);
            b.setTypeComplement(type);
            b.setDescription(desc);
            b.setVolume(volume);
            c = b;
        } else {
            Complement comp = new Complement();
            comp.setNom(nom);
            comp.setPrix(prix);
            comp.setTypeComplement(type);
            comp.setDescription(desc);
            c = comp;
        }

        Complement saved = service.create(c, imgFile);
        System.out.println("Complément créé id=" + saved.getId());
        if (saved.getImage() != null) {
            System.out.println("Image URL: " + saved.getImage());
            System.out.println("Image public_id: " + saved.getImagePublicId());
        } else {
            System.out.println("Aucune image fournie.");
        }
    }

    private void listComplements() throws Exception {
        List<Complement> list = service.findAll();
        System.out.println("Compléments:");
        if (list.isEmpty()){
            System.out.println(" (aucun)");
            return;
        }
        list.forEach(c -> {
            String extra = "";
            if (c instanceof Frite){
                extra = " taille=" + ((Frite)c).getTaille();
            }
            else{
                if (c instanceof Boisson){
                    extra = " volume=" + ((Boisson)c).getVolume();
                } 
            } 
            System.out.println(" - id=" + c.getId() + " nom=" + c.getNom() + " type=" + c.getTypeComplement() + " prix=" + c.getPrix() + extra);
        });
    }

    private void listByType() throws Exception {
        System.out.print("Type (FRITE/BOISSON): ");
        String t = scanner.nextLine().trim().toUpperCase();
        ComplementType type;
        try {
            type = ComplementType.valueOf(t);
        } catch (Exception ex) {
            System.out.println("Type invalide."); return;
        }
        List<Complement> list = service.findAll()
                .stream()
                .filter(c -> c.getTypeComplement() == type)
                .collect(Collectors.toList());

        System.out.println("Compléments de type " + type + ":");
        if (list.isEmpty()) {
            System.out.println(" (aucun)");
            return;
        }
        list.forEach(c -> {
            String extra = "";
            if (c instanceof Frite){
                extra = " taille=" + ((Frite)c).getTaille();
            } 
            else{
                if (c instanceof Boisson){
                    extra = " volume=" + ((Boisson)c).getVolume();
                } 
            } 
            System.out.println(" - id=" + c.getId() + " nom=" + c.getNom() + " prix=" + c.getPrix() + extra);
        });
    }

    private void updateComplement() throws Exception {
        System.out.print("Id du complément à modifier: ");
        Long id;
        try {
            id = Long.valueOf(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Id invalide."); return;
        }
        Complement existing = service.findById(id);
        if (existing == null) { 
            System.out.println("Complément introuvable.");
             return; 
        }

        System.out.println("Nom actuel: " + existing.getNom());
        System.out.print("Nouveau nom (laisser vide pour ne pas changer): ");
        String nom = scanner.nextLine().trim();
        if (!nom.isEmpty()){
            existing.setNom(nom);
        } 

        System.out.println("Prix actuel: " + existing.getPrix());
        System.out.print("Nouveau prix (laisser vide pour ne pas changer): ");
        String prixStr = scanner.nextLine().trim();
        if (!prixStr.isEmpty()) {
            try {
                existing.setPrix(new BigDecimal(prixStr));
            } catch (Exception ex) {
                System.out.println("Prix invalide."); return;
            }
        }

        System.out.println("Type actuel: " + existing.getTypeComplement());
        System.out.print("Nouveau type (FRITE/BOISSON) (laisser vide pour ne pas changer): ");
        String typeStr = scanner.nextLine().trim();
        if (!typeStr.isEmpty()) {
            try {
                existing.setTypeComplement(ComplementType.valueOf(typeStr.toUpperCase()));
            } catch (Exception ex) {
                System.out.println("Type invalide."); return;
            }
        }

        if (existing.getTypeComplement() == ComplementType.FRITE) {
            String cur = (existing instanceof Frite) ? ((Frite) existing).getTaille() : null;
            System.out.println("Taille actuelle: " + (cur == null ? "-" : cur));
            System.out.print("Nouvelle taille (laisser vide pour ne pas changer): ");
            String taille = scanner.nextLine().trim();
            if (!taille.isEmpty()) {
                if (existing instanceof Frite){
                    ((Frite) existing).setTaille(taille);
                } 
                else {
                    Frite f = new Frite();
                    f.setId(existing.getId());
                    f.setNom(existing.getNom());
                    f.setPrix(existing.getPrix());
                    f.setTypeComplement(existing.getTypeComplement());
                    f.setDescription(existing.getDescription());
                    f.setImage(existing.getImage());
                    f.setImagePublicId(existing.getImagePublicId());
                    f.setTaille(taille);
                    existing = f;
                }
            }
        } else if (existing.getTypeComplement() == ComplementType.BOISSON) {
            String cur = (existing instanceof Boisson) ? ((Boisson) existing).getVolume() : null;
            System.out.println("Volume actuel: " + (cur == null ? "-" : cur));
            System.out.print("Nouveau volume (laisser vide pour ne pas changer): ");
            String volume = scanner.nextLine().trim();
            if (!volume.isEmpty()) {
                if (existing instanceof Boisson){
                    ((Boisson) existing).setVolume(volume);
                } 
                else {
                    Boisson b = new Boisson();
                    b.setId(existing.getId());
                    b.setNom(existing.getNom());
                    b.setPrix(existing.getPrix());
                    b.setTypeComplement(existing.getTypeComplement());
                    b.setDescription(existing.getDescription());
                    b.setImage(existing.getImage());
                    b.setImagePublicId(existing.getImagePublicId());
                    b.setVolume(volume);
                    existing = b;
                }
            }
        }

        System.out.print("Chemin nouveau fichier image (laisser vide pour ne pas changer): ");
        String path = scanner.nextLine().trim();
        File imgFile = null;
        if (!path.isEmpty()) {
            imgFile = new File(path);
            if (!imgFile.exists() || !imgFile.isFile()) {
                System.out.println("Fichier introuvable : " + path);
                return;
            }
            if (!isAllowedImageExtension(imgFile)) {
                System.out.println("Format non supporté. Utilise jpg/jpeg/png/webp.");
                return;
            }
            long maxBytes = 10L * 1024 * 1024;
            if (imgFile.length() > maxBytes) {
                System.out.println("Fichier trop volumineux (>10MB).");
                return;
            }
        }

        Complement updated = service.update(id, existing, imgFile);
        System.out.println("Complément mis à jour id=" + updated.getId());
        if (updated.getImage() != null) {
            System.out.println("Image URL: " + updated.getImage());
            System.out.println("Image public_id: " + updated.getImagePublicId());
        }
    }

    private void deleteComplement() throws Exception {
        System.out.print("Id du complément à supprimer: ");
        Long id;
        try {
            id = Long.valueOf(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Id invalide."); return;
        }
        service.delete(id);
        System.out.println("Complément supprimé.");
    }

    private boolean isAllowedImageExtension(File f) {
        String name = f.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".webp");
    }
}
