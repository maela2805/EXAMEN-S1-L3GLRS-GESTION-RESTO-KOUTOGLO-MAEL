<?php

namespace App\Services\Livreur;

use App\Entity\User;
use App\Entity\Livreur;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

class LivreurService implements LivreurServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em,
        private UserPasswordHasherInterface $hasher
    ) {}

    public function create(array $data): void
    {
        $user = new User();
        $user
            ->setNom($data['nom'])
            ->setPrenom($data['prenom'])
            ->setTelephone($data['telephone'])
            ->setLogin($data['login'])
            ->setPassword(
                $this->hasher->hashPassword($user, $data['password'])
            )
            ->setRole('LIVREUR')
            ->setTypeUser('LIVREUR');

        $this->em->persist($user);
        $this->em->flush();
        $livreur = new Livreur();
        $livreur
            ->setUser($user)
            ->setMatriculeMoto($data['matricule_moto'] ?? null)
            ->setDisponible(true);

        $this->em->persist($livreur);
        $this->em->flush();
    }

    public function getAll(): array
    {
        return $this->em->createQuery("
            SELECT l, u
            FROM App\Entity\Livreur l
            JOIN l.user u
            ORDER BY u.nom
        ")->getResult();
    }
}
