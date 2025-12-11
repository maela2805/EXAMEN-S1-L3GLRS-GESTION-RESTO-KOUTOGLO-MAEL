package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommandeComplement {
    private Long id;
    private Commande commande;
    private Complement complement;
    private Integer quantite;
    private BigDecimal prixUnitaire;
}
