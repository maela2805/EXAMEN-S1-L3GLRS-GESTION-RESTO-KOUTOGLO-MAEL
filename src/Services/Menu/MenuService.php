<?php

namespace App\Services\Menu;

use App\Entity\Menu;
use App\Entity\Product;
use App\Entity\Burger;
use App\Entity\Complement;
use App\Entity\MenuBurger;
use App\Entity\MenuComplement;
use Doctrine\ORM\EntityManagerInterface;
use App\Services\Cloudinary\CloudinaryServiceInterface;
use Symfony\Component\DependencyInjection\ParameterBag\ParameterBagInterface;
use Symfony\Component\HttpFoundation\File\UploadedFile;

class MenuService implements MenuServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em,
        private CloudinaryServiceInterface $cloudinary
    ) {}

    public function getAll(): array
    {
        return $this->em->createQuery("
            SELECT m, p
            FROM App\Entity\Menu m
            JOIN m.product p
            WHERE p.typeproduit = 'MENU'
        ")->getResult();
    }

    public function create(Product $product,?string $imagePath = null,int $burgerId,int $friteId,int $boissonId): void {
        $product->setTypeproduit('MENU');
        if ($imagePath) {
            $upload = $this->cloudinary->upload($imagePath);
            $product->setImage($upload['secure_url']);
            $product->setImagePublicId($upload['public_id']);
        }
        $burger  = $this->em->getRepository(Burger::class)->find($burgerId);
        $frite   = $this->em->getRepository(Complement::class)->find($friteId);
        $boisson = $this->em->getRepository(Complement::class)->find($boissonId);

        if (!$burger || !$frite || !$boisson) {
            throw new \RuntimeException('Éléments du menu invalides');
        }
        $product->setPrix(
            $burger->getProduct()->getPrix()
            + $frite->getPrix()
            + $boisson->getPrix()
        );
        $this->em->persist($product);
        $this->em->flush();
        $menu = new Menu();
        $menu->setProduct($product);
        $menu->setDescription($product->getDescription());
        $this->em->persist($menu);
        $menuBurger = new MenuBurger();
        $menuBurger
            ->setMenu($menu)
            ->setBurger($burger);

        $this->em->persist($menuBurger);
        $mcFrite = new MenuComplement();
        $mcFrite
            ->setMenu($menu)
            ->setComplement($frite)
            ->setRole('FRITE');

        $this->em->persist($mcFrite);
        $mcBoisson = new MenuComplement();
        $mcBoisson
            ->setMenu($menu)
            ->setComplement($boisson)
            ->setRole('BOISSON');

        $this->em->persist($mcBoisson);
        $this->em->flush();
    }


    public function find(int $id): ?Menu
    {
        return $this->em->getRepository(Menu::class)->find($id);
    }

    public function update(Menu $menu,Product $product,int $burgerId,int $friteId,int $boissonId,?string $imagePath = null): void {
        if ($imagePath) {
            $this->cloudinary->delete($product->getImagePublicId());
            $upload = $this->cloudinary->upload($imagePath);
            $product->setImage($upload['secure_url']);
            $product->setImagePublicId($upload['public_id']);
        }

        $this->em->persist($product);
        $this->em->createQuery(
            'DELETE FROM App\Entity\MenuBurger mb WHERE mb.menu = :menu'
        )->setParameter('menu', $menu)->execute();

        $this->em->createQuery(
            'DELETE FROM App\Entity\MenuComplement mc WHERE mc.menu = :menu'
        )->setParameter('menu', $menu)->execute();
        $burger = $this->em->getRepository(Burger::class)->find($burgerId);
        $mb = new MenuBurger();
        $mb->setMenu($menu)->setBurger($burger);
        $this->em->persist($mb);
        foreach ([$friteId, $boissonId] as $cid) {
            $complement = $this->em->getRepository(Complement::class)->find($cid);
            $mc = new MenuComplement();
            $mc->setMenu($menu)->setComplement($complement);
            $this->em->persist($mc);
        }
        $prix = 
            $burger->getProduct()->getPrix()
            + $this->em->getRepository(Complement::class)->find($friteId)->getPrix()
            + $this->em->getRepository(Complement::class)->find($boissonId)->getPrix();

        $product->setPrix($prix);

        $this->em->flush();
    }



    public function delete(Menu $menu): void
    {
        $product = $menu->getProduct();

        $this->em->createQuery(
            'DELETE FROM App\Entity\MenuBurger mb WHERE mb.menu = :menu'
        )->setParameter('menu', $menu)->execute();

        $this->em->createQuery(
            'DELETE FROM App\Entity\MenuComplement mc WHERE mc.menu = :menu'
        )->setParameter('menu', $menu)->execute();

        $this->em->remove($menu);
        $this->em->remove($product);

        $this->em->flush();
    }

}
