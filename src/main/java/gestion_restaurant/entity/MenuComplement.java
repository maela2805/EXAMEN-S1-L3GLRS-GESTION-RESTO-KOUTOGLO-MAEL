package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuComplement {
    private Long id;
    private Long menuId;
    private Long complementId;
    private Integer quantite = 1;
    private String role;
    private Menu menu;
    private Complement complement;
    public void setMenu(Menu menu) {
        this.menu = menu;
        this.menuId = (menu != null ? menu.getId() : null);
    }
    public void setComplement(Complement complement) {
        this.complement = complement;
        this.complementId = (complement != null ? complement.getId() : null);
    }
}
