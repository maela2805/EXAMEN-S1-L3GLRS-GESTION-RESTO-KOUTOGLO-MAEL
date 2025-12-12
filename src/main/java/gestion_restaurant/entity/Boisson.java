package gestion_restaurant.entity;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class Boisson extends Complement {
    private String volume;

    public Boisson(Long id, String nom, java.math.BigDecimal prix, String image,
                   ComplementType typeComplement, java.time.Instant createdAt,
                   String imagePublicId, String description, String volume) {
        super(id, nom, prix, image, typeComplement, createdAt, imagePublicId, description);
        this.volume = volume;
    }
}
