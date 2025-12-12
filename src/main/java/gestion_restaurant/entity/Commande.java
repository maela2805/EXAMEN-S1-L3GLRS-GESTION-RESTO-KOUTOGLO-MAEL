package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Commande {
    private Long id;
    private Client client;
    private Instant dateCommande = Instant.now();
    private BigDecimal montantTotal = BigDecimal.ZERO;
    private StatutCommande statut;
    private Boolean isPaye = Boolean.FALSE;
    private List<CommandeItem> items = new ArrayList<>();
    private List<CommandeComplement> complements = new ArrayList<>();
    private Livraison livraison;
}
