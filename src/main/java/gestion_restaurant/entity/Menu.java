package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Menu extends Product {

    private List<MenuBurger> menuBurgers = new ArrayList<>();
    private List<MenuComplement> menuComplements = new ArrayList<>();

    public Menu(Long id, String nom, String image, String imagePublicId,
                BigDecimal prix, ProductType type, Instant createdAt, String description) {
        super(id, nom, image, imagePublicId, prix, type, createdAt, description);
    }
}

