<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Collections\ArrayCollection;

#[ORM\Entity]
#[ORM\Table(name: 'zone')]
class Zone
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private int $id;

    #[ORM\Column(type: 'integer')]
    private int $tarif;

    #[ORM\OneToMany(mappedBy: 'zone', targetEntity: Quartier::class)]
    private Collection $quartiers;

    public function __construct()
    {
        $this->quartiers = new ArrayCollection();
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function getTarif(): int
    {
        return $this->tarif;
    }

    public function setTarif(int $tarif): self
    {
        $this->tarif = $tarif;
        return $this;
    }

    public function getQuartiers(): Collection
    {
        return $this->quartiers;
    }
}
