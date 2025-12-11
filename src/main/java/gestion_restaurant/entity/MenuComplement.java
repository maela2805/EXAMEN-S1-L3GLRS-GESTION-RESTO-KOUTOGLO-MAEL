package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuComplement {
    private MenuComplementId id;
    private Menu menu;
    private Complement complement;
    private Integer quantite = 1;
    private String role; // ex "frite" ou "boisson"
}
