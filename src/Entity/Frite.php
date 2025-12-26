<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'frite')]
class Frite
{
    #[ORM\Id]
    #[ORM\Column(type: 'bigint')]
    private int $id;

    #[ORM\OneToOne]
    #[ORM\JoinColumn(name: 'id', referencedColumnName: 'id')]
    private Complement $complement;

    #[ORM\Column(type: 'text')]
    private string $taille;

    public function setComplement(Complement $c): self {
        $this->complement = $c;
        $this->id = $c->getId();
        return $this;
    }

     public function getTaille(): string
    {
        return $this->taille;
    }

    public function setTaille(string $taille): self
    {
        $this->taille = $taille;
        return $this;
    }
}

?>
