<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
#[ORM\Entity]
#[ORM\Table(name: 'menu')]
class Menu
{
    #[ORM\Id]
    #[ORM\Column(type: 'bigint')]
    private int $id;

    #[ORM\OneToOne]
    #[ORM\JoinColumn(name: 'id', referencedColumnName: 'id')]
    private Product $product;

    public function setProduct(Product $product): self
    {
        $this->product = $product;
        $this->id = $product->getId();
        return $this;
    }

    public function getProduct(): Product
    {
        return $this->product;
    }
    #[ORM\Column(type: 'text', nullable: true)]
    private ?string $description = null;

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;
        return $this;
    }

   


}
