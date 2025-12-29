<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
#[ORM\Entity]
#[ORM\Table(name: 'menu_burger')]
class MenuBurger
{
    #[ORM\Id]
    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'menu_id', referencedColumnName: 'id')]
    private Menu $menu;

    #[ORM\Id]
    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: 'burger_id', referencedColumnName: 'id')]
    private Burger $burger;

    public function setMenu(Menu $menu): self
    {
        $this->menu = $menu;
        return $this;
    }

    public function setBurger(Burger $burger): self
    {
        $this->burger = $burger;
        return $this;
    }
}
