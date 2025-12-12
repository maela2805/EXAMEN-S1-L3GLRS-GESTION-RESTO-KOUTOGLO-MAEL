package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommandeItem {
    private Long id;
    private Commande commande;
    private Product product;
    private Integer quantite;
    private BigDecimal prixUnitaire;
    private String remarque;
    private List<CommandeMenuComplement> menuComplements = new ArrayList<>();
}
