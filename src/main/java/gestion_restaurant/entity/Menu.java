package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Menu extends Product {

    private Set<MenuBurger> menuBurgers = new HashSet<>();
    private Set<MenuComplement> menuComplements = new HashSet<>();

    public Menu(Long id, String nom, String image, String imagePublicId,
                BigDecimal prix, ProductType type, Instant createdAt, String description) {
        super(id, nom, image, imagePublicId, prix, type, createdAt, description);
    }
}

