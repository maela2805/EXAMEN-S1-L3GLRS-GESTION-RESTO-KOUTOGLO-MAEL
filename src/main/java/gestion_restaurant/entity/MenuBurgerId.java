package gestion_restaurant.entity;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuBurgerId implements Serializable {
    private Long menuId;
    private Long burgerId;
}
