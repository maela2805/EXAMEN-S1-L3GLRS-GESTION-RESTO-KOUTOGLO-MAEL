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
public class Zone {
    private Integer id;
    private BigDecimal tarif;
    private List<Quartier> quartiers = new ArrayList<>();
    private List<Livraison> livraisons = new ArrayList<>();
}
