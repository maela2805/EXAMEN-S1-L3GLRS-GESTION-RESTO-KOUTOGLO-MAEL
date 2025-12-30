<?php
namespace App\Services\Commande;

use App\Entity\Commande;
use App\Entity\Livreur;

interface CommandeServiceInterface
{
    public function getFiltered(array $filters): array;
    public function changerStatut(Commande $commande, string $statut): void;
    public function assignerLivreur(array $commandes, Livreur $livreur): void;
}
