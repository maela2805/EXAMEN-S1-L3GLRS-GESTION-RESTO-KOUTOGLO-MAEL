<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'complement')]
class Complement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'bigint')]
    private ?int $id = null;

    #[ORM\Column(type: 'text')]
    private string $nom;

    #[ORM\Column(type: 'decimal', precision: 12, scale: 2)]
    private string $prix;

    #[ORM\Column(type: 'string')]
    private string $typecomplement;

    #[ORM\Column(type: 'text', nullable: true)]
    private ?string $description = null;

    #[ORM\Column(type: 'text', nullable: true)]
    private ?string $image = null;

    #[ORM\Column(type: 'string', length: 255, nullable: true)]
    private ?string $image_public_id = null;


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

    public function getPrix(): float
    {
        return (float) $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = (string) $prix;
        return $this;
    }

    public function getTypecomplement(): string
    {
        return $this->typecomplement;
    }

    public function setTypecomplement(string $type): self
    {
        $this->typecomplement = $type;
        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $d): self
    {
        $this->description = $d;
        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $img): self
    {
        $this->image = $img;
        return $this;
    }

    public function getImagePublicId(): ?string
    {
        return $this->image_public_id;
    }

    public function setImagePublicId(?string $id): self
    {
        $this->image_public_id = $id;
        return $this;
    }
     #[ORM\OneToOne(mappedBy: 'complement', targetEntity: Frite::class)]
    private ?Frite $frite = null;

    #[ORM\OneToOne(mappedBy: 'complement', targetEntity: Boisson::class)]
    private ?Boisson $boisson = null;

    public function getFrite(): ?Frite
    {
        return $this->frite;
    }

    public function setFrite(?Frite $frite): self
    {
        $this->frite = $frite;
        return $this;
    }

    public function getBoisson(): ?Boisson
    {
        return $this->boisson;
    }

    public function setBoisson(?Boisson $boisson): self
    {
        $this->boisson = $boisson;
        return $this;
    }
}
