package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuBurger {
    private Long id;
    private Long menuId;
    private Long burgerId;
    private Integer quantite = 1;
    private Menu menu;
    private Burger burger;
    public void setMenu(Menu menu) {
        this.menu = menu;
        this.menuId = (menu != null ? menu.getId() : null);
    }
    public void setBurger(Burger burger) {
        this.burger = burger;
        this.burgerId = (burger != null ? burger.getId() : null);
    }
}
