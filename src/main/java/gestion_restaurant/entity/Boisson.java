package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Boisson extends Complement {
    private String volume;

    public Boisson(Long id, String nom, java.math.BigDecimal prix, String image, ComplementType typeComplement, String volume) {
        super(id, nom, prix, image, typeComplement, null);
        this.volume = volume;
    }
}
