<?php

namespace App\Controller\Gestionnaire;


use App\Entity\Commande;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class DashboardController extends AbstractController
{
    #[Route('/gestionnaire/dashboard', name: 'gestionnaire_dashboard')]
    public function index(EntityManagerInterface $em): Response
    {
        $enCours = $em->createQuery(
            "SELECT COUNT(c.id) FROM App\Entity\Commande c WHERE c.statut = 'ENCOURS'"
        )->getSingleScalarResult();

        $terminees = $em->createQuery(
            "SELECT COUNT(c.id) FROM App\Entity\Commande c WHERE c.statut = 'TERMINE'"
        )->getSingleScalarResult();

        $recette = $em->createQuery(
            "SELECT COALESCE(SUM(c.montantTotal), 0)
             FROM App\Entity\Commande c
             WHERE c.statut = 'TERMINE'"
        )->getSingleScalarResult();

        $commandesRecentes = $em->getRepository(Commande::class)
            ->findBy([], ['dateCommande' => 'DESC'], 5);

        return $this->render('gestionnaire/dashboard/index.html.twig', [
            'enCours' => $enCours,
            'terminees' => $terminees,
            'recette' => $recette,
            'commandesRecentes' => $commandesRecentes,
        ]);
    }
}
