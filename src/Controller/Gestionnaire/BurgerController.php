<?php

namespace App\Controller\Gestionnaire;

use App\Services\Burger\BurgerServiceInterface;
use App\Form\BurgerType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;


#[Route('/gestionnaire/burgers')]
class BurgerController extends AbstractController
{
    public function __construct(
        private BurgerServiceInterface $burgerService
    ) {}

    #[Route('', name: 'gestionnaire_burgers')]
    public function index(): Response
    {
        return $this->render('gestionnaire/burger/index.html.twig', [
            'burgers' => $this->burgerService->getAll()
        ]);
    }

    #[Route('/new', name: 'gestionnaire_burger_new')]
    public function new(Request $request): Response
    {
        $form = $this->createForm(BurgerType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();

            $this->burgerService->create(
                $form->getData(),
                $imageFile ? $imageFile->getPathname() : null
            );

            return $this->redirectToRoute('gestionnaire_burgers');
        }

        return $this->render('gestionnaire/burger/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/edit/{id}', name: 'gestionnaire_burger_edit')]
    public function edit(int $id, Request $request): Response
    {
        $burger = $this->burgerService->find($id);

        if (!$burger) {
            throw $this->createNotFoundException('Burger introuvable');
        }

        $product = $burger->getProduct();

        if ($request->isMethod('POST')) {
            $imageFile = $request->files->get('image');

            $this->burgerService->update(
                $id,
                $request->request->all(),
                $imageFile ? $imageFile->getPathname() : null
            );

            return $this->redirectToRoute('gestionnaire_burgers');
        }

        return $this->render('gestionnaire/burger/edit.html.twig', [
            'burger'  => $burger,
            'product' => $product,
        ]);
    }


    #[Route('/delete/{id}', name: 'gestionnaire_burger_delete', methods: ['POST'])]
    public function delete(int $id): Response
    {
        $this->burgerService->delete($id);
        return $this->redirectToRoute('gestionnaire_burgers');
    }
}

