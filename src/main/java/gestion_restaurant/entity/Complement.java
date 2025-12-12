package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor 
 @ToString
public class Complement {
    private Long id;
    private String nom;
    private BigDecimal prix;
    private String image;
    private ComplementType typeComplement;
    private Instant createdAt = Instant.now();
    private String imagePublicId;
    private String description;

    public Complement(Long id, String nom, BigDecimal prix, String image,
                      ComplementType typeComplement, Instant createdAt,
                      String imagePublicId, String description) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.typeComplement = typeComplement;
        this.createdAt = createdAt;
        this.imagePublicId = imagePublicId;
        this.description = description;
    }
}
