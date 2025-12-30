<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Commande;
use App\Services\Commande\CommandeServiceInterface;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/gestionnaire/commandes')]
class CommandeController extends AbstractController
{
    #[Route('', name: 'gestionnaire_commandes')]
    public function index(
        Request $request,
        CommandeServiceInterface $commandeService
    ): Response {
        $filters = [
            'statut' => $request->query->get('statut'),
        ];

        return $this->render('gestionnaire/commande/index.html.twig', [
            'commandes' => $commandeService->getFiltered($filters),
            'filters'   => $filters
        ]);
    }

    #[Route('/statut/{id}', name: 'gestionnaire_commande_statut', methods: ['POST'])]
    public function changerStatut(
        Commande $commande,
        Request $request,
        CommandeServiceInterface $commandeService
    ): Response {
        $commandeService->changerStatut(
            $commande,
            $request->request->get('statut')
        );

        return $this->redirectToRoute('gestionnaire_commandes');
    }

    #[Route('/livraisons', name: 'gestionnaire_livraisons')]
    public function livraisons(
        EntityManagerInterface $em
    ): Response {
        $commandes = $em->getRepository(Commande::class)
            ->findBy(
                ['statut' => 'PRETE'],
                ['dateCommande' => 'DESC']
            );

        return $this->render('gestionnaire/commande/livraison.html.twig', [
            'commandes' => $commandes
        ]);
    }
}
