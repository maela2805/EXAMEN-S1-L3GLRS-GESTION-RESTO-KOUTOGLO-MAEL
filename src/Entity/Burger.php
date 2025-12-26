<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

// #[ORM\Entity]
// #[ORM\Table(name: 'burger')]
// class Burger
// {
//     #[ORM\Id]
//     #[ORM\Column(type: 'bigint')]
//     private int $id;

//     #[ORM\OneToOne]
//     #[ORM\JoinColumn(name: 'id', referencedColumnName: 'id')]
//     private Product $product;

//     #[ORM\Column(type: 'text', nullable: true)]
//     private ?string $description = null;

//     public function getId(): int { 
//         return $this->id; 
//     }

//     public function getProduct(): Product { 
//         return $this->product; 
//     }

//     public function setProduct(Product $product): self
//     {
//         $this->product = $product;
//         $this->id = $product->getId();
//         return $this;
//     }

//     public function getDescription(): ?string { 
//         return $this->description; 
//     }
//     public function setDescription(?string $d): self { 
//         $this->description = $d; return $this; 
//     }
// }

#[ORM\Entity]
#[ORM\Table(name: 'burger')]
class Burger
{
    #[ORM\Id]
    #[ORM\Column(type: 'bigint')]
    private int $id;

    #[ORM\OneToOne]
    #[ORM\JoinColumn(name: 'id', referencedColumnName: 'id')]
    private Product $product;

    public function getId(): int
    {
        return $this->id;
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


    public function setProduct(Product $product): self
    {
        $this->product = $product;
        $this->id = $product->getId();

        return $this;
    }
}
