<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'livreur')]
class Livreur
{
    #[ORM\Id]
    #[ORM\Column(type: 'bigint')]
    private int $id;

    #[ORM\OneToOne(inversedBy: 'livreur')]
    #[ORM\JoinColumn(name: 'id', referencedColumnName: 'id')]
    private User $user;

    #[ORM\Column(type: 'text', nullable: true)]
    private ?string $matricule_moto = null;

    #[ORM\Column(type: 'boolean')]
    private bool $disponible = true;

    public function getId(): int
    {
        return $this->id;
    }

    public function getUser(): User
    {
        return $this->user;
    }

    public function setUser(User $user): self
    {
        $this->user = $user;
        $this->id = $user->getId();
        return $this;
    }

    public function getMatriculeMoto(): ?string
    {
        return $this->matricule_moto;
    }

    public function setMatriculeMoto(?string $matricule_moto): self
    {
        $this->matricule_moto = $matricule_moto;
        return $this;
    }

    public function isDisponible(): bool
    {
        return $this->disponible;
    }

    public function setDisponible(bool $disponible): self
    {
        $this->disponible = $disponible;
        return $this;
    }
}
