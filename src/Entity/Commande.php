<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'commande')]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'bigint')]
    private ?int $id = null;

    #[ORM\Column(type: 'datetime')]
    private \DateTimeInterface $dateCommande;


    #[ORM\Column(type: 'decimal', precision: 12, scale: 2)]
    private string $montantTotal = '0.00';

    #[ORM\Column(type: 'string', length: 50)]
    private string $statut;

    #[ORM\Column(type: 'boolean')]
    private bool $isPaye = false;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'client_id', nullable: true)]
    private ?User $client = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'livraison_id', nullable: true)]
    private ?Livraison $livraison = null;

    public function __construct()
    {
        $this->dateCommande = new \DateTime();
        $this->statut = 'EN_COURS';
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateCommande(): \DateTimeInterface
    {
        return $this->dateCommande;
    }

    public function setDateCommande(\DateTimeInterface $dateCommande): self
    {
        $this->dateCommande = $dateCommande;
        return $this;
    }

    public function getMontantTotal(): string
    {
        return $this->montantTotal;
    }

    public function setMontantTotal(string $montantTotal): self
    {
        $this->montantTotal = $montantTotal;
        return $this;
    }

    public function getStatut(): string
    {
        return $this->statut;
    }

    public function setStatut(string $statut): self
    {
        $this->statut = $statut;
        return $this;
    }

    public function isPaye(): bool
    {
        return $this->isPaye;
    }

    public function setIsPaye(bool $isPaye): self
    {
        $this->isPaye = $isPaye;
        return $this;
    }

    public function getClient(): ?User
    {
        return $this->client;
    }

    public function setClient(?User $client): self
    {
        $this->client = $client;
        return $this;
    }

    public function getLivraison(): ?Livraison
    {
        return $this->livraison;
    }

    public function setLivraison(?Livraison $livraison): self
    {
        $this->livraison = $livraison;
        return $this;
    }
}
