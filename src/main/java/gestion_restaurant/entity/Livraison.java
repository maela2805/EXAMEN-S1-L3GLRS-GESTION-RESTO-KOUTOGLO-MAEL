package gestion_restaurant.entity;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Livraison {
    private Long id;
    private Instant dateLivraison;
    private StatutLivraison statut;
    private Zone zone;
    private Livreur livreur;
    private Long commandeId;
    private Commande commande;
}
