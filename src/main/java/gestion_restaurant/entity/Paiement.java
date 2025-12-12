package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Paiement {
    private Long id;
    private Commande commande;
    private BigDecimal montant;
    private Instant datePaiement = Instant.now();
    private ModePaiement modePaiement;
}
