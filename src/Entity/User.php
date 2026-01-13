<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Core\User\PasswordAuthenticatedUserInterface;

#[ORM\Entity]
#[ORM\Table(name: 'users')]
class User implements UserInterface, PasswordAuthenticatedUserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'bigint')]
    private ?int $id = null;

    #[ORM\Column(type: 'text')]
    private string $nom;

    #[ORM\Column(type: 'text')]
    private string $prenom;

    #[ORM\Column(type: 'text')]
    private string $telephone;

    #[ORM\Column(type: 'text')]
    private string $login;

    #[ORM\Column(type: 'text')]
    private string $password;

    #[ORM\Column(type: 'string')]
    private string $role;

    #[ORM\Column(type: 'text')]
    private string $type_user;

    #[ORM\Column(type: 'datetime')]
    private \DateTimeInterface $created_at;

    #[ORM\OneToOne(mappedBy: 'user', targetEntity: Livreur::class)]
    private ?Livreur $livreur = null;

    public function __construct()
    {
        $this->created_at = new \DateTime();
    }

    public function getUserIdentifier(): string
    {
        return $this->login;
    }

    public function getRoles(): array
    {
        return ['ROLE_' . $this->role];
    }


    public function eraseCredentials(): void
    {
        
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;
        return $this;
    }

    public function getPrenom(): string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;
        return $this;
    }

    public function getTelephone(): string
    {
        return $this->telephone;
    }

    public function setTelephone(string $telephone): self
    {
        $this->telephone = $telephone;
        return $this;
    }

    public function getLogin(): string
    {
        return $this->login;
    }

    public function setLogin(string $login): self
    {
        $this->login = $login;
        return $this;
    }

    public function getPassword(): string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;
        return $this;
    }

    public function getRole(): string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;
        return $this;
    }

    public function getTypeUser(): string
    {
        return $this->type_user;
    }

    public function setTypeUser(string $type_user): self
    {
        $this->type_user = $type_user;
        return $this;
    }

    public function getCreatedAt(): \DateTimeInterface
    {
        return $this->created_at;
    }

    public function getLivreur(): ?Livreur
    {
        return $this->livreur;
    }

    public function setLivreur(?Livreur $livreur): self
    {
        $this->livreur = $livreur;
        return $this;
    }
}
