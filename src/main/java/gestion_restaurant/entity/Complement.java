package gestion_restaurant.entity;

import lombok.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Complement {
    private Long id;
    private String nom;
    private BigDecimal prix;
    private String image;
    private ComplementType typeComplement;
    private Set<MenuComplement> menuComplements = new HashSet<>();
}
