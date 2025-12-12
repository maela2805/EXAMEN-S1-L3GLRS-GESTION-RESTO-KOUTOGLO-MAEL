package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Quartier {
    private Integer id;
    private String libelle;
    private Zone zone;
}
