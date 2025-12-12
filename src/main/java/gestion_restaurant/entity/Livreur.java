package gestion_restaurant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Livreur extends User {
    public Livreur(Long id, String nom, String prenom, String telephone, String login, String password, RoleType role, java.time.Instant createdAt) {
        super(id, nom, prenom, telephone, login, password, role, createdAt);
    }
}
