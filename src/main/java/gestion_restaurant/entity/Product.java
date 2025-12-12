package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Product {

    private Long id;
    private String nom;
    private String image;
    private String imagePublicId;
    private BigDecimal prix;
    private ProductType typeProduit;
    private Instant createdAt = Instant.now();

    private String description;
}

