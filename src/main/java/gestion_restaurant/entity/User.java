package gestion_restaurant.entity;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class User {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String login;
    private String password;
    private RoleType role;
    private Instant createdAt = Instant.now();
}
