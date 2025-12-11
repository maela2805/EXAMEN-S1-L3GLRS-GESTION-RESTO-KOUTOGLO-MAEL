package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Frite extends Complement {
    private String taille;

    public Frite(Long id, String nom, java.math.BigDecimal prix, String image, ComplementType typeComplement, String taille) {
        super(id, nom, prix, image, typeComplement, null);
        this.taille = taille;
    }
}
