<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Quartier;
use App\Form\QuartierType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\HttpFoundation\Response;

#[Route('/gestionnaire/quartiers')]
class QuartierController extends AbstractController
{
    #[Route('', name: 'gestionnaire_quartiers')]
    public function index(EntityManagerInterface $em): Response
    {
        return $this->render('gestionnaire/quartier/index.html.twig', [
            'quartiers' => $em->getRepository(Quartier::class)->findAll()
        ]);
    }

    #[Route('/new', name: 'gestionnaire_quartier_new')]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $quartier = new Quartier();
        $form = $this->createForm(QuartierType::class, $quartier);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($quartier);
            $em->flush();

            return $this->redirectToRoute('gestionnaire_quartiers');
        }

        return $this->render('gestionnaire/quartier/new.html.twig', [
            'form' => $form->createView()
        ]);
    }

    #[Route('/edit/{id}', name: 'gestionnaire_quartier_edit')]
    public function edit(
        Quartier $quartier,
        Request $request,
        EntityManagerInterface $em
    ): Response {
        $form = $this->createForm(QuartierType::class, $quartier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            return $this->redirectToRoute('gestionnaire_quartiers');
        }

        return $this->render('gestionnaire/quartier/edit.html.twig', [
            'form' => $form->createView(),
            'quartier' => $quartier,
        ]);
    }
    #[Route('/delete/{id}', name: 'gestionnaire_quartier_delete', methods: ['POST'])]
    public function delete(
        Quartier $quartier,
        EntityManagerInterface $em
    ): Response {
        $em->remove($quartier);
        $em->flush();

        return $this->redirectToRoute('gestionnaire_quartiers');
    }
}
