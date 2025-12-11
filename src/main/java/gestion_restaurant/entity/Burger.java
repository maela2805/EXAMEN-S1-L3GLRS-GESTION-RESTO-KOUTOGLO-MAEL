package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Burger extends Product {
    public Burger(Long id, String nom, String image, String imagePublicId,
                BigDecimal prix, ProductType type, Instant createdAt, String description) {

        super(id, nom, image, imagePublicId, prix, type, createdAt, description);
    }
}

