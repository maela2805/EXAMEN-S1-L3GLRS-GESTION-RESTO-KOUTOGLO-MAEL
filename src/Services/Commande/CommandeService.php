<?php

namespace App\Services\Commande;

use App\Entity\Commande;
use App\Entity\Livreur;
use App\Entity\Livraison;
use Doctrine\ORM\EntityManagerInterface;

class CommandeService implements CommandeServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em
    ) {}

    public function getFiltered(array $filters): array
    {
        $qb = $this->em->getRepository(Commande::class)
            ->createQueryBuilder('c');

        if (!empty($filters['statut'])) {
            $qb->andWhere('c.statut = :statut')
               ->setParameter('statut', $filters['statut']);
        }

        return $qb
            ->orderBy('c.dateCommande', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function changerStatut(Commande $commande, string $statut): void
    {
        $commande->setStatut($statut);
        $this->em->flush();
    }

    public function assignerLivreur(array $commandes, Livreur $livreur): void
    {
        $livraison = new Livraison();
        $livraison->setLivreur($livreur);

        foreach ($commandes as $commande) {
            $commande->setLivraison($livraison);
            $commande->setStatut('EN_LIVRAISON');
        }

        $livreur->setDisponible(false);

        $this->em->persist($livraison);
        $this->em->flush();
    }
}
