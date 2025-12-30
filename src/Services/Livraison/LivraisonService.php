<?php

namespace App\Services\Livraison;

use App\Entity\Commande;
use App\Entity\Livreur;
use App\Entity\Livraison;
use Doctrine\ORM\EntityManagerInterface;

class LivraisonService
{
    public function __construct(
        private EntityManagerInterface $em
    ) {}

    /**
     * @param int[] $commandeIds
     */
    public function creerLivraison(array $commandeIds, Livreur $livreur): void
{
    foreach ($commandeIds as $id) {

        $commande = $this->em
            ->getRepository(Commande::class)
            ->find($id);

        if (!$commande || $commande->getLivraison() !== null) {
            continue;
        }

        $livraison = new Livraison();
        $livraison->setLivreur($livreur);
        $livraison->setStatut('AFFECTEE');
        $livraison->setDateLivraison(new \DateTime());

        $commande->setLivraison($livraison);

        $this->em->persist($livraison);
    }

    $livreur->setDisponible(false);
    $this->em->flush();
}

}

