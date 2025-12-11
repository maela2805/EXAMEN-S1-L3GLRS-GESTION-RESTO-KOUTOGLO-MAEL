package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommandeMenuComplement {
    private Long id;
    private CommandeItem commandeItem;
    private Complement complement;
    private Integer quantite;
    private BigDecimal prixUnitaire;
    private String remarque;
}
