package gestion_restaurant.service.impl;

import gestion_restaurant.cloud.CloudinaryService;
import gestion_restaurant.entity.Burger;
import gestion_restaurant.repository.ProductRepository;
import gestion_restaurant.service.BurgerService;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.cloudinary.utils.ObjectUtils;

public class BurgerServiceImpl implements BurgerService {

    private final ProductRepository productRepo;
    private final CloudinaryService cloudinary;

    public BurgerServiceImpl(ProductRepository productRepo, CloudinaryService cloudinary) {
        this.productRepo = productRepo;
        this.cloudinary = cloudinary;
    }

    @Override
    public Burger create(Burger burger, File imageFile) throws Exception {
        if (imageFile != null && imageFile.exists()) {

            Map<String, Object> opts = ObjectUtils.asMap(
                    "folder", "gestion_restaurant/burgers",
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true,
                    "transformation", new com.cloudinary.Transformation()
                            .width(800)
                            .height(600)
                            .crop("fill")
            );
            CloudinaryService.UploadResult res = cloudinary.upload(imageFile, opts);
            burger.setImage(res.secureUrl);
            burger.setImagePublicId(res.publicId);
        }

        return (Burger) productRepo.save(burger);
    }


    @Override
    public Burger updateImage(Long id, File newImage) throws Exception {
        Burger existing = (Burger) productRepo.findById(id);
        if (existing == null){
            throw new IllegalArgumentException("Burger introuvable");
        } 

        if (existing.getImagePublicId() != null) {
            cloudinary.delete(existing.getImagePublicId());
        }

        var res = cloudinary.upload(newImage, null);
        existing.setImage(res.secureUrl);
        existing.setImagePublicId(res.publicId);

        return (Burger) productRepo.update(existing);
    }

    @Override
    public void delete(Long id) throws Exception {
        Burger b = (Burger) productRepo.findById(id);
        if (b == null){
            return;
        } 

        if (b.getImagePublicId() != null) {
            cloudinary.delete(b.getImagePublicId());
        }

        productRepo.delete(id);
    }

    @Override
    public Burger findById(Long id) throws Exception {
        return (Burger) productRepo.findById(id);
    }

    @Override
    public List<Burger> findAll() throws Exception {
        return productRepo.findAllBurgers();
    }
}
