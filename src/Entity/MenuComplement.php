<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'menu_complement')]
class MenuComplement
{
    #[ORM\Id]
    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'menu_id', referencedColumnName: 'id')]
    private Menu $menu;

    #[ORM\Id]
    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'complement_id', referencedColumnName: 'id')]
    private Complement $complement;

    public function setMenu(Menu $menu): self
    {
        $this->menu = $menu;
        return $this;
    }

    public function setComplement(Complement $c): self
    {
        $this->complement = $c;
        return $this;
    }
     #[ORM\Column(type: 'string', length: 20)]
    private string $role;

    public function getRole(): string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;
        return $this;
    }
}
