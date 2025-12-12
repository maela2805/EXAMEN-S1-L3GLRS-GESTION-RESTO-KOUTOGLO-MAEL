package gestion_restaurant.service.impl;

import com.cloudinary.utils.ObjectUtils;
import gestion_restaurant.cloud.CloudinaryService;
import gestion_restaurant.entity.Menu;
import gestion_restaurant.entity.ProductType;
import gestion_restaurant.entity.MenuBurger;
import gestion_restaurant.entity.MenuComplement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.repository.MenuRepository;
import gestion_restaurant.repository.MenuBurgerRepository;
import gestion_restaurant.repository.MenuComplementRepository;
import gestion_restaurant.repository.ProductRepository;
import gestion_restaurant.service.MenuService;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepo;
    private final ProductRepository productRepo;
    private final MenuBurgerRepository menuBurgerRepo;
    private final MenuComplementRepository menuComplementRepo;
    private final CloudinaryService cloudinary;

    public MenuServiceImpl(MenuRepository menuRepo,
                           ProductRepository productRepo,
                           MenuBurgerRepository menuBurgerRepo,
                           MenuComplementRepository menuComplementRepo,
                           CloudinaryService cloudinary) {
        this.menuRepo = menuRepo;
        this.productRepo = productRepo;
        this.menuBurgerRepo = menuBurgerRepo;
        this.menuComplementRepo = menuComplementRepo;
        this.cloudinary = cloudinary;
    }
    @Override
    public Menu create(Menu menu, File imageFile) throws Exception {
        if (menu == null) throw new IllegalArgumentException("menu is null");
        if (menu.getCreatedAt() == null){
            menu.setCreatedAt(Instant.now());
        }
        menu.setTypeProduit(ProductType.MENU);
        if (imageFile != null && imageFile.exists() && cloudinary != null) {
            boolean cloudConfigured;
            try {
                cloudConfigured = cloudinary.isCloudinaryConfigured();
            } catch (NoSuchMethodError | UnsupportedOperationException ex) {
                cloudConfigured = true; 
            }

            if (cloudConfigured) {
                Map<String, Object> opts = ObjectUtils.asMap(
                        "folder", "gestion_restaurant/menus",
                        "use_filename", true,
                        "unique_filename", false,
                        "overwrite", true,
                        "transformation", new com.cloudinary.Transformation()
                                .width(800).height(600).crop("fill")
                );

                CloudinaryService.UploadResult res = cloudinary.upload(imageFile, opts);
                menu.setImage(res.secureUrl);
                menu.setImagePublicId(res.publicId);
            }
        }
        Menu saved = menuRepo.save(menu);
        if (saved == null || saved.getId() == null) {
            throw new IllegalStateException("Impossible de sauvegarder le menu (id null)");
        }
        Long menuId = saved.getId();
        System.out.println("[DEBUG] saved menu id=" + menuId + " burgers=" + (menu.getMenuBurgers() == null ? 0 : menu.getMenuBurgers().size())
                + " complements=" + (menu.getMenuComplements() == null ? 0 : menu.getMenuComplements().size()));

        try {
            menuBurgerRepo.deleteByMenuId(menuId);
        } catch (Exception ex) {
            System.err.println("[WARN] deleteByMenuId(menu) failed: " + ex.getMessage());
        }
        if (menu.getMenuBurgers() != null) {
        for (MenuBurger mb : menu.getMenuBurgers()) {
            if (mb == null) continue;
            if (mb.getBurgerId() == null) {
                if (mb.getBurger() != null) {
                    mb.setBurgerId(mb.getBurger().getId());
                }
            }
            if (mb.getBurgerId() == null) {
                throw new IllegalArgumentException("MenuBurger doit contenir un Burger avec id");
            }
            mb.setMenuId(menuId);
            if (mb.getQuantite() == null){
                mb.setQuantite(1);
            } 
            menuBurgerRepo.save(mb);
        }
    }


    try {
        menuComplementRepo.deleteByMenuId(menuId);
    } catch (Exception ex) {
        System.err.println("[WARN] deleteByMenuId(menu) for complements failed: " + ex.getMessage());
    }

    if (menu.getMenuComplements() != null) {
    for (MenuComplement mc : menu.getMenuComplements()) {
        if (mc == null) continue;

        // 🔥 Garantir que l’ID est bien défini
        if (mc.getComplementId() == null) {
            if (mc.getComplement() != null) {
                mc.setComplementId(mc.getComplement().getId());
            }
        }

        if (mc.getComplementId() == null) {
            throw new IllegalArgumentException("MenuComplement doit contenir un Complement avec id");
        }

        mc.setMenuId(menuId);

        if (mc.getQuantite() == null) mc.setQuantite(1);

        menuComplementRepo.save(mc);
    }
}


    return saved;
}



    @Override
    public Menu update(Long id, Menu menu, File imageFile) throws Exception {
        if (id == null) throw new IllegalArgumentException("id required");
        if (menu == null) throw new IllegalArgumentException("menu required");

        Menu existing = menuRepo.findById(id);
        if (existing == null){
            throw new IllegalArgumentException("menu introuvable");
        } 

        existing.setNom(menu.getNom() == null ? existing.getNom() : menu.getNom());
        existing.setDescription(menu.getDescription() == null ? existing.getDescription() : menu.getDescription());
        existing.setPrix(menu.getPrix() == null ? existing.getPrix() : menu.getPrix());
        existing.setMenuBurgers(menu.getMenuBurgers());
        existing.setMenuComplements(menu.getMenuComplements());
        if (imageFile != null && imageFile.exists() && cloudinary != null && isCloudinaryConfigured()) {
            if (existing.getImagePublicId() != null) {
                try { cloudinary.delete(existing.getImagePublicId()); } catch (Exception ignored) {}
            }
            Map<String,Object> opts = ObjectUtils.asMap(
                    "folder", "gestion_restaurant/menus",
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true,
                    "transformation", new com.cloudinary.Transformation().width(800).height(600).crop("fill")
            );
            CloudinaryService.UploadResult res = cloudinary.upload(imageFile, opts);
            existing.setImage(res.secureUrl);
            existing.setImagePublicId(res.publicId);
        }

        return menuRepo.save(existing);
    }

    @Override
    public List<Menu> findAll() throws Exception {
        return menuRepo.findAll();
    }

    @Override
    public Menu findById(Long id) throws Exception {
        return menuRepo.findById(id);
    }

    @Override
    public void delete(Long id) throws Exception {
        if (id == null){
            return;
        } 
        Menu m = menuRepo.findById(id);
        if (m != null && m.getImagePublicId() != null && cloudinary != null && isCloudinaryConfigured()) {
            try { cloudinary.delete(m.getImagePublicId()); } catch (Exception ignored) {}
        }
        menuRepo.deleteById(id);
    }

    private boolean isCloudinaryConfigured() {
        try {
            return cloudinary != null;
        } catch (Throwable t) {
            return false;
        }
    }
}
