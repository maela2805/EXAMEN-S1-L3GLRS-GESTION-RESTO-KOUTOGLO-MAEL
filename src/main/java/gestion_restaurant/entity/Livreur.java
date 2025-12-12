package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Livreur extends User {

    private String matriculeMoto;
    private boolean disponible = true;

    public Livreur(Long id, String nom, String prenom, String telephone,
                   String login, String password, RoleType role, java.time.Instant createdAt,
                   String matriculeMoto, boolean disponible) {

        super(id, nom, prenom, telephone, login, password, role, createdAt);
        this.matriculeMoto = matriculeMoto;
        this.disponible = disponible;
    }
}

