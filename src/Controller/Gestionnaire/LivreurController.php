<?php

namespace App\Controller\Gestionnaire;

use App\Form\LivreurType;
use App\Services\Livreur\LivreurServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/gestionnaire/livreurs')]
class LivreurController extends AbstractController
{
    #[Route('', name: 'gestionnaire_livreurs')]
    public function index(
        LivreurServiceInterface $livreurService
    ): Response {
        return $this->render('gestionnaire/livreur/index.html.twig', [
            'livreurs' => $livreurService->getAll()
        ]);
    }
    #[Route('/new', name: 'gestionnaire_livreur_new')]
    public function new(
        Request $request,
        LivreurServiceInterface $livreurService
    ): Response {
        $form = $this->createForm(LivreurType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $livreurService->create($form->getData());

            return $this->redirectToRoute('gestionnaire_livreurs');
        }

        return $this->render('gestionnaire/livreur/new.html.twig', [
            'form' => $form->createView()
        ]);
    }
}
