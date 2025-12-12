package gestion_restaurant.view;

import java.util.Scanner;

import gestion_restaurant.entity.Livreur;
import gestion_restaurant.service.LivreurService;
import gestion_restaurant.util.Validator;

public class LivreurView {

    private final LivreurService service;
    private final Scanner scanner = new Scanner(System.in);

    public LivreurView(LivreurService service) {
        this.service = service;
    }

    public void start() throws Exception {
        while (true) {
            System.out.println("\n=== Gestion Livreurs ===");
            System.out.println("1) Ajouter un livreur");
            System.out.println("2) Lister les livreurs");
            System.out.println("3) Modifier un livreur");
            System.out.println("4) Supprimer un livreur");
            System.out.println("0) Retour");
            System.out.print("Choix: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> create();
                case "2" -> list();
                case "3" -> update();
                case "4" -> delete();
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void create() throws Exception {
        Livreur l = new Livreur();

        System.out.print("Nom: ");
        l.setNom(scanner.nextLine().trim());

        System.out.print("Prenom: ");
        l.setPrenom(scanner.nextLine().trim());

        String tel;
        while (true) {
            System.out.print("Téléphone (ex: 781234567): ");
            tel = scanner.nextLine().trim();
            if (Validator.isValidTelephone(tel)) break;
            System.out.println("❌ Numéro invalide ! Il doit commencer par 77 ou 78 et contenir 9 chiffres.");
        }
        l.setTelephone(tel);

        System.out.print("Login: ");
        l.setLogin(scanner.nextLine().trim());

        System.out.print("Mot de passe: ");
        l.setPassword(scanner.nextLine().trim());
        System.out.print("Matricule moto: ");
        l.setMatriculeMoto(scanner.nextLine().trim());
        Livreur saved = service.create(l);
        System.out.println("Livreur ajouté, id=" + saved.getId());
    }


    private void list() {
        try {
            for (Livreur l : service.findAll()) {
                System.out.println("ID=" + l.getId()
                        + " | " + l.getNom() + " " + l.getPrenom()
                        + " | Moto=" + l.getMatriculeMoto()
                        + " | dispo=" + l.isDisponible());
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void update() {
        System.out.print("ID du livreur: ");
        Long id = Long.valueOf(scanner.nextLine());

        try {
            Livreur l = service.findById(id);
            if (l == null) {
                System.out.println("Livreur introuvable.");
                return;
            }

            System.out.println("Nom actuel: " + l.getNom());
            System.out.print("Nouveau nom (enter pour ignorer): ");
            String s = scanner.nextLine();
            if (!s.isEmpty()){
                l.setNom(s);
            } 

            System.out.println("Matricule moto: " + l.getMatriculeMoto());
            System.out.print("Nouveau matricule (enter pour ignorer): ");
            s = scanner.nextLine();
            if (!s.isEmpty()){
                l.setMatriculeMoto(s);
            } 

            service.update(l);
            System.out.println("Livreur mis à jour.");

        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private void delete() {
        System.out.print("ID du livreur: ");
        Long id = Long.valueOf(scanner.nextLine());

        try {
            service.delete(id);
            System.out.println("Livreur supprimé.");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}

