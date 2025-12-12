package gestion_restaurant.entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class Frite extends Complement {
    private String taille;

    public Frite(Long id, String nom, java.math.BigDecimal prix, String image,
                 ComplementType typeComplement, java.time.Instant createdAt,
                 String imagePublicId, String description, String taille) {
        super(id, nom, prix, image, typeComplement, createdAt, imagePublicId, description);
        this.taille = taille;
    }
}
