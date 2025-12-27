<?php

namespace App\Services\Quartier;

use App\Entity\Quartier;
use Doctrine\ORM\EntityManagerInterface;

class QuartierService implements QuartierServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em
    ) {}

    public function getAll(): array
    {
        return $this->em->getRepository(Quartier::class)->findAll();
    }

    public function create(Quartier $quartier): void
    {
        $this->em->persist($quartier);
        $this->em->flush();
    }

    public function find(int $id): ?Quartier
    {
        return $this->em->getRepository(Quartier::class)->find($id);
    }

    public function delete(int $id): void
    {
        $quartier = $this->find($id);

        if (!$quartier) {
            return;
        }

        $this->em->remove($quartier);
        $this->em->flush();
    }
}
