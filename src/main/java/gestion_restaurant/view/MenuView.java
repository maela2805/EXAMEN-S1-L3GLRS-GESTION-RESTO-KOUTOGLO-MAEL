package gestion_restaurant.view;

import gestion_restaurant.entity.Menu;
import gestion_restaurant.entity.MenuBurger;
import gestion_restaurant.entity.MenuComplement;
import gestion_restaurant.service.MenuService;
import gestion_restaurant.entity.ComplementType;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final MenuService menuService;
    private final Scanner scanner = new Scanner(System.in);

    public MenuView(MenuService menuService) {
        this.menuService = menuService;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMenu();
            String c = scanner.nextLine().trim();
            try {
                switch (c) {
                    case "1": createMenu(); break;
                    case "2": listMenus(); break;
                    case "3": updateMenu(); break;
                    case "4": deleteMenu(); break;
                    case "0": exit = true; break;
                    default: System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Gestion Menus ===");
        System.out.println("1) Créer un menu");
        System.out.println("2) Lister tous les menus");
        System.out.println("3) Mettre à jour un menu");
        System.out.println("4) Supprimer un menu");
        System.out.println("0) Retour");
        System.out.print("Choix: ");
    }

    private void createMenu() throws Exception {
        Menu m = new Menu();

        System.out.print("Nom: ");
        m.setNom(scanner.nextLine().trim());

        System.out.print("Prix (ex: 1000.00): ");
        try {
            String priceInput = scanner.nextLine().trim();
            if (!priceInput.isEmpty()){
                m.setPrix(new BigDecimal(priceInput));
            }
            else { 
                System.out.println("Prix requis."); 
                return; 
            }
        } catch (Exception ex) { 
            System.out.println("Prix invalide."); 
            return; 
        }

        System.out.print("Description: ");
        m.setDescription(scanner.nextLine().trim());
        if (m.getMenuBurgers() == null){
             m.setMenuBurgers(new ArrayList<>());
        }
        if (m.getMenuComplements() == null){
            m.setMenuComplements(new ArrayList<>());
        } 

        System.out.print("Ids burgers (séparés par ',') (laisser vide si aucun): ");
        String ids = scanner.nextLine().trim();
        if (!ids.isEmpty()) {
            String[] arr = ids.split(",");
            for (String s : arr) {
                String raw = s.trim();
                if (raw.isEmpty()) continue;
                try {
                    Long burgerId = Long.valueOf(raw);
                    MenuBurger mb = new MenuBurger();
                    var burger = new gestion_restaurant.entity.Burger();
                    burger.setId(burgerId);
                    mb.setBurger(burger);
                    System.out.print("Quantité pour ce burger (enter pour 1): ");
                    String q = scanner.nextLine().trim();
                    mb.setQuantite(q.isEmpty() ? 1 : Integer.valueOf(q));
                    m.getMenuBurgers().add(mb);
                } catch (Exception ex) {
                    System.out.println("Id burger invalide: " + raw);
                }
            }
        }

        System.out.print("Ids complements (format id:type:quantité; ex: 5:FRITE:1;7:BOISSON:1) (laisser vide si none): ");
        String comps = scanner.nextLine().trim();
        if (!comps.isEmpty()) {
            String[] arr = comps.split(";");
            for (String token : arr) {
                String t = token.trim();
                if (t.isEmpty()){
                    continue;
                }
                try {
                    String[] p = t.split(":");
                    if (p.length < 2){
                        throw new IllegalArgumentException("format attendu id:TYPE[:quantite]");
                    } 
                    Long compId = Long.valueOf(p[0].trim());
                    ComplementType type = ComplementType.valueOf(p[1].trim().toUpperCase());
                    int quant = p.length >= 3 && !p[2].trim().isEmpty() ? Integer.parseInt(p[2].trim()) : 1;
                    MenuComplement mc = new MenuComplement();
                    var comp = new gestion_restaurant.entity.Complement();
                    comp.setId(compId);
                    mc.setComplement(comp);
                    mc.setQuantite(quant);
                    mc.setRole(type.name());
                    m.getMenuComplements().add(mc);
                } catch (Exception ex) {
                    System.out.println("Token complement invalide: " + token + " (" + ex.getMessage() + ")");
                }
            }
        }

        System.out.print("Chemin fichier image (laisser vide si pas d'image): ");
        String path = scanner.nextLine().trim();
        File imageFile = path.isEmpty() ? null : new File(path);
        Menu saved = menuService.create(m, imageFile);
        System.out.println("Menu créé id=" + saved.getId());
    }

    private void listMenus() throws Exception {
        List<Menu> list = menuService.findAll();
        if (list == null || list.isEmpty()) {
            System.out.println(" (aucun)");
            return;
        }
        for (Menu m : list) {
            System.out.println("----");
            System.out.println("id=" + m.getId() + " nom=" + m.getNom() + " prix=" + m.getPrix());
            System.out.println("desc=" + m.getDescription());
            System.out.println("Burgers:");
            if (m.getMenuBurgers() != null) {
                m.getMenuBurgers().forEach(mb ->
                    System.out.println(" - burgerId=" + (mb.getBurger() == null ? "?" : mb.getBurger().getId()) + " q=" + mb.getQuantite())
                );
            } else System.out.println(" (aucun)");

            System.out.println("Complements:");
            if (m.getMenuComplements() != null) {
                m.getMenuComplements().forEach(mc ->
                    System.out.println(" - compId=" + (mc.getComplement() == null ? "?" : mc.getComplement().getId()) + " q=" + mc.getQuantite() + " role=" + mc.getRole())
                );
            } else System.out.println(" (aucun)");

            System.out.println("image=" + m.getImage());
        }
    }

    private void updateMenu() throws Exception {
        System.out.print("Id du menu à modifier: ");
        Long id;
        try {
            id = Long.valueOf(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Id invalide");
            return;
        }
        Menu existing = menuService.findById(id);
        if (existing == null) {
            System.out.println("Menu introuvable");
            return;
        }
        if (existing.getMenuBurgers() == null){
            existing.setMenuBurgers(new ArrayList<>());
        }
        if (existing.getMenuComplements() == null){
            existing.setMenuComplements(new ArrayList<>());
        }
        System.out.println("Nom actuel: " + existing.getNom());
        System.out.print("Nouveau nom (laisser vide pour ne pas changer): ");
        String nom = scanner.nextLine().trim();
        if (!nom.isEmpty()){
            existing.setNom(nom);
        }
        System.out.println("Prix actuel: " + existing.getPrix());
        System.out.print("Nouveau prix (laisser vide pour ne pas changer): ");
        String p = scanner.nextLine().trim();
        if (!p.isEmpty()) {
            try { existing.setPrix(new BigDecimal(p)); }
            catch (Exception ex) { System.out.println("Prix invalide, on n'a pas modifié."); }
        }

        System.out.println("Description actuelle: " + existing.getDescription());
        System.out.print("Nouvelle description (laisser vide pour ne pas changer): ");
        String desc = scanner.nextLine().trim();
        if (!desc.isEmpty()){
            existing.setDescription(desc);
        }

        System.out.print("Remplacer burgers (oui/non) ? ");
        String rep = scanner.nextLine().trim();
        if ("oui".equalsIgnoreCase(rep)) {
            existing.getMenuBurgers().clear();
            System.out.print("Ids burgers (séparés par ','): ");
            String ids = scanner.nextLine().trim();
            if (!ids.isEmpty()) {
                for (String s : ids.split(",")) {
                    String raw = s.trim();
                    if (raw.isEmpty()){
                        continue;
                    }
                    try {
                        Long burgerId = Long.valueOf(raw);
                        MenuBurger mb = new MenuBurger();
                        var burger = new gestion_restaurant.entity.Burger();
                        burger.setId(burgerId);
                        mb.setBurger(burger);
                        System.out.print("Quantité pour ce burger (enter pour 1): ");
                        String q = scanner.nextLine().trim();
                        mb.setQuantite(q.isEmpty() ? 1 : Integer.valueOf(q));
                        existing.getMenuBurgers().add(mb);
                    } catch (Exception ex) { 
                        System.out.println("Id burger invalide: " + raw); 
                    }
                }
            }
        }

        System.out.print("Remplacer complements (oui/non) ? ");
        rep = scanner.nextLine().trim();
        if ("oui".equalsIgnoreCase(rep)) {
            existing.getMenuComplements().clear();
            System.out.print("Ids complements (format id:type:quantité; ex: 5:FRITE:1;7:BOISSON:1): ");
            String comps = scanner.nextLine().trim();
            if (!comps.isEmpty()) {
                for (String token : comps.split(";")) {
                    String t = token.trim();
                    if (t.isEmpty()) continue;
                    try {
                        String[] p2 = t.split(":");
                        if (p2.length < 2){
                            throw new IllegalArgumentException("format attendu id:TYPE[:quantite]");
                        }
                        Long compId = Long.valueOf(p2[0].trim());
                        ComplementType type = ComplementType.valueOf(p2[1].trim().toUpperCase());
                        int quant = p2.length >= 3 && !p2[2].trim().isEmpty() ? Integer.parseInt(p2[2].trim()) : 1;
                        MenuComplement mc = new MenuComplement();
                        var comp = new gestion_restaurant.entity.Complement();
                        comp.setId(compId);
                        mc.setComplement(comp);
                        mc.setQuantite(quant);
                        mc.setRole(type.name());
                        existing.getMenuComplements().add(mc);
                    } catch (Exception ex) { System.out.println("Token complement invalide: " + token + " (" + ex.getMessage() + ")"); }
                }
            }
        }

        System.out.print("Chemin nouveau fichier image (laisser vide pour ne pas remplacer): ");
        String path = scanner.nextLine().trim();
        File imageFile = path.isEmpty() ? null : new File(path);
        Menu updated = menuService.update(id, existing, imageFile);
        System.out.println("Menu mis à jour id=" + updated.getId());
    }
    
    private void deleteMenu() throws Exception {
        System.out.print("Id du menu à supprimer: ");
        Long id;
        try {
            id = Long.valueOf(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Id invalide");
            return;
        }
        menuService.delete(id);
        System.out.println("Menu supprimé.");
    }
}
