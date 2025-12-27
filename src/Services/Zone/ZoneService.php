<?php

namespace App\Services\Zone;

use App\Entity\Zone;
use Doctrine\ORM\EntityManagerInterface;

class ZoneService implements ZoneServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em
    ) {}

    public function getAll(): array
    {
        return $this->em->getRepository(Zone::class)->findAll();
    }

    public function create(Zone $zone): void
    {
        $this->em->persist($zone);
        $this->em->flush();
    }

    public function find(int $id): ?Zone
    {
        return $this->em->getRepository(Zone::class)->find($id);
    }

    public function delete(int $id): void
    {
        $zone = $this->find($id);

        if (!$zone) {
            return;
        }

        $this->em->remove($zone);
        $this->em->flush();
    }
}
