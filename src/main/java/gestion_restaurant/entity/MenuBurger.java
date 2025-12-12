package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuBurger {
    private MenuBurgerId id;
    private Menu menu;
    private Burger burger;
    private Integer quantite = 1;
}
