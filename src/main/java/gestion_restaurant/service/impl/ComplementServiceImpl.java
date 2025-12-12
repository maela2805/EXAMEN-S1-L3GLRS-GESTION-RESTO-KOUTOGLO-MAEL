package gestion_restaurant.service.impl;

import com.cloudinary.utils.ObjectUtils;
import gestion_restaurant.cloud.CloudinaryService;
import gestion_restaurant.entity.Complement;
import gestion_restaurant.entity.ComplementType;
import gestion_restaurant.entity.Frite;
import gestion_restaurant.entity.Boisson;
import gestion_restaurant.repository.ComplementRepository;
import gestion_restaurant.repository.FriteRepository;
import gestion_restaurant.repository.BoissonRepository;
import gestion_restaurant.service.ComplementService;

import java.io.File;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ComplementServiceImpl implements ComplementService {

    private final ComplementRepository complementRepo;
    private final FriteRepository friteRepo;     
    private final BoissonRepository boissonRepo; 
    private final CloudinaryService cloudinary;

    public ComplementServiceImpl(ComplementRepository complementRepo,
                                FriteRepository friteRepo,
                                BoissonRepository boissonRepo,
                                CloudinaryService cloudinary) {
        this.complementRepo = complementRepo;
        this.friteRepo = friteRepo;
        this.boissonRepo = boissonRepo;
        this.cloudinary = cloudinary;
    }

    @Override
    public Complement create(Complement c, File imageFile) throws Exception {
        if (c.getCreatedAt() == null) c.setCreatedAt(Instant.now());

        if (imageFile != null && imageFile.exists() && cloudinary != null && cloudinary.isAvailable()) {
            String folder = "gestion_restaurant/complements";
            if (c.getTypeComplement() == ComplementType.FRITE){
                folder = "gestion_restaurant/complements/frites";
            }
            else{
                if (c.getTypeComplement() == ComplementType.BOISSON) folder = "gestion_restaurant/complements/boissons";
            } 

            Map<String,Object> opts = ObjectUtils.asMap(
                    "folder", folder,
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true,
                    "transformation", new com.cloudinary.Transformation().width(800).height(600).crop("fill")
            );

            CloudinaryService.UploadResult res = cloudinary.upload(imageFile, opts);
            c.setImage(res.secureUrl);
            c.setImagePublicId(res.publicId);
        }

        Complement saved = complementRepo.save(c);
        if (saved.getTypeComplement() == ComplementType.FRITE && friteRepo != null) {
            if (saved instanceof Frite) friteRepo.save((Frite) saved);
            else {
                Frite f = new Frite(saved.getId(), saved.getNom(), saved.getPrix(), saved.getImage(), saved.getTypeComplement(),
                        saved.getCreatedAt(), saved.getImagePublicId(), saved.getDescription(), null);
                friteRepo.save(f);
            }
        } else if (saved.getTypeComplement() == ComplementType.BOISSON && boissonRepo != null) {
            if (saved instanceof Boisson) boissonRepo.save((Boisson) saved);
            else {
                Boisson b = new Boisson(saved.getId(), saved.getNom(), saved.getPrix(), saved.getImage(), saved.getTypeComplement(),
                        saved.getCreatedAt(), saved.getImagePublicId(), saved.getDescription(), null);
                boissonRepo.save(b);
            }
        }

        return saved;
    }

    @Override
    public Complement update(Long id, Complement c, File imageFile) throws Exception {
        Complement existing = complementRepo.findById(id);
        if (existing == null) throw new IllegalArgumentException("Complement introuvable");

        existing.setNom(c.getNom());
        existing.setPrix(c.getPrix());
        existing.setDescription(c.getDescription());
        existing.setTypeComplement(c.getTypeComplement());

        if (imageFile != null && imageFile.exists() && cloudinary != null && cloudinary.isAvailable()) {
            if (existing.getImagePublicId() != null) {
                try { cloudinary.delete(existing.getImagePublicId()); } catch (Exception ex) { /* log */ }
            }
            String folder = "gestion_restaurant/complements";
            if (existing.getTypeComplement() == ComplementType.FRITE) folder = "gestion_restaurant/complements/frites";
            else if (existing.getTypeComplement() == ComplementType.BOISSON) folder = "gestion_restaurant/complements/boissons";

            Map<String,Object> opts = ObjectUtils.asMap(
                    "folder", folder,
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true,
                    "transformation", new com.cloudinary.Transformation().width(800).height(600).crop("fill")
            );
            CloudinaryService.UploadResult res = cloudinary.upload(imageFile, opts);
            existing.setImage(res.secureUrl);
            existing.setImagePublicId(res.publicId);
        }

        return complementRepo.update(existing);
    }

    @Override
    public List<Complement> findAll() throws Exception {
        return complementRepo.findAll();
    }

    @Override
    public Complement findById(Long id) throws Exception {
        return complementRepo.findById(id);
    }

    @Override
    public void delete(Long id) throws Exception {
        Complement c = complementRepo.findById(id);
        if (c == null){
            return;
        } 
        if (cloudinary != null && cloudinary.isAvailable() && c.getImagePublicId() != null) {
            try { 
                cloudinary.delete(c.getImagePublicId()); 
            } catch (Exception ex) { 

            }
        }
        complementRepo.delete(id);
        if (friteRepo != null){
            friteRepo.deleteById(id);
        } 
        if (boissonRepo != null){
            boissonRepo.deleteById(id);
        } 
    }
}
