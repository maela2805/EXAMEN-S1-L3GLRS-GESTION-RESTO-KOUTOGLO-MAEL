<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'boisson')]
class Boisson
{
    #[ORM\Id]
    #[ORM\Column(type: 'bigint')]
    private int $id;

    #[ORM\OneToOne]
    #[ORM\JoinColumn(name: 'id', referencedColumnName: 'id')]
    private Complement $complement;

    #[ORM\Column(type: 'text')]
    private string $volume;

    public function setComplement(Complement $c): self {
        $this->complement = $c;
        $this->id = $c->getId();
        return $this;
    }

    public function getVolume(): string
    {
        return $this->volume;
    }

    public function setVolume(string $volume): self
    {
        $this->volume = $volume;
        return $this;
    }
}

?>