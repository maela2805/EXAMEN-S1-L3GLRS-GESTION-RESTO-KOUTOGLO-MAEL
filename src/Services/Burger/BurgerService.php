<?php

namespace App\Services\Burger;

use App\Entity\Product;
use App\Entity\Burger;
use App\Services\Cloudinary\CloudinaryServiceInterface;
use Doctrine\ORM\EntityManagerInterface;

class BurgerService implements BurgerServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em,
        private CloudinaryServiceInterface $cloudinary
    ) {}

    public function getAll(): array
    {
        return $this->em->createQuery("
            SELECT b, p
            FROM App\Entity\Burger b
            JOIN b.product p
            WHERE p.typeproduit = 'BURGER'
        ")->getResult();
    }

    public function create(Product $product, ?string $imagePath = null): void
    {
        $product->setTypeproduit('BURGER');

        if ($imagePath) {
            $upload = $this->cloudinary->upload($imagePath);
            $product->setImage($upload['secure_url']);
            $product->setImagePublicId($upload['public_id']);
        }
        $this->em->persist($product);
        $this->em->flush();
        $burger = new Burger();
        $burger->setProduct($product);
        $burger->setDescription($product->getDescription());

        $this->em->persist($burger);
        $this->em->flush();
    }



    public function update(int $id, array $data, ?string $imagePath): void
    {
        $burger = $this->em->getRepository(Burger::class)->find($id);
        $product = $burger->getProduct();

        $product
            ->setNom($data['nom'])
            ->setPrix($data['prix'])
            ->setDescription($data['description']);
        
        $burger->setDescription($data['description']);

        if ($imagePath) {
            $this->cloudinary->delete($product->getImagePublicId());
            $upload = $this->cloudinary->upload($imagePath);
            $product->setImage($upload['secure_url']);
            $product->setImagePublicId($upload['public_id']);
        }

        $this->em->flush();
    }

    public function delete(int $id): void
    {
        $burger = $this->em->getRepository(Burger::class)->find($id);
        if (!$burger) {
            return;
        }

        $product = $burger->getProduct();

        if ($product->getImagePublicId()) {
            $this->cloudinary->delete($product->getImagePublicId());
        }

        $this->em->remove($burger);
        $this->em->remove($product);
        $this->em->flush();
    }

    public function find(int $id): ?Burger
    {
        return $this->em->getRepository(Burger::class)->find($id);
    }

}
