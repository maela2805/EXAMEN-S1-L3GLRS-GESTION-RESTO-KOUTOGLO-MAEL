<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Commande;
use App\Entity\Livreur;
use App\Entity\Livraison;
use App\Entity\Zone;
use App\Services\Livraison\LivraisonService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/gestionnaire/livraisons')]
class LivraisonController extends AbstractController
{
    #[Route('/creer', name: 'gestionnaire_livraison_creer', methods: ['GET'])]
    public function form(EntityManagerInterface $em): Response
    {
        return $this->render('gestionnaire/livraison/creation.html.twig', [
            'commandes' => $em->getRepository(Commande::class)
                ->createQueryBuilder('c')
                ->where('c.livraison IS NULL')
                ->getQuery()
                ->getResult(),
            'zones' => $em->getRepository(Zone::class)->findAll(),
            'livreurs' => $em->getRepository(Livreur::class)
                ->findBy(['disponible' => true])
        ]);
    }
    #[Route('/creer', name: 'creer_livraison', methods: ['POST'])]
    public function creer(
        Request $request,
        LivraisonService $livraisonService,
        EntityManagerInterface $em
    ): Response {
        $commandeIds = $request->request->all('commandes');

        $livreur = $em->getRepository(Livreur::class)
            ->find($request->request->get('livreur'));

        $livraisonService->creerLivraison($commandeIds, $livreur);

        return $this->redirectToRoute('gestionnaire_commandes');
    }
}
