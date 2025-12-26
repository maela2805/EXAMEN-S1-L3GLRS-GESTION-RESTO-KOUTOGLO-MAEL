<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Complement;
use App\Form\ComplementType;
use App\Services\Complement\ComplementServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/gestionnaire/complements')]
class ComplementController extends AbstractController
{
    public function __construct(
        private ComplementServiceInterface $complementService
    ) {}
    #[Route('', name: 'gestionnaire_complements')]
    public function index(): Response
    {
        return $this->render('gestionnaire/complement/index.html.twig', [
            'complements' => $this->complementService->getAll()
        ]);
    }
    #[Route('/new', name: 'gestionnaire_complement_new')]
    public function new(Request $request): Response
    {
        $complement = new Complement();

        $form = $this->createForm(ComplementType::class, $complement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $imageFile = $form->get('imageFile')->getData();
            $extra = [
                'taille' => $form->has('taille') ? $form->get('taille')->getData() : null,
                'volume' => $form->has('volume') ? $form->get('volume')->getData() : null,
            ];

            $this->complementService->create(
                $complement,
                $imageFile ? $imageFile->getPathname() : null,
                $extra
            );

            return $this->redirectToRoute('gestionnaire_complements');
        }

        return $this->render('gestionnaire/complement/new.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    #[Route('/edit/{id}', name: 'gestionnaire_complement_edit')]
    public function edit(int $id, Request $request): Response
    {
        $complement = $this->complementService->find($id);

        if (!$complement) {
            throw $this->createNotFoundException('Complément introuvable');
        }

        if ($request->isMethod('POST')) {

            $this->complementService->update(
                $id,
                $request->request->all(),
                $request->files->get('image')?->getPathname()
            );

            return $this->redirectToRoute('gestionnaire_complements');
        }

        return $this->render('gestionnaire/complement/edit.html.twig', [
            'complement' => $complement,
            'frite'      => $complement->getTypecomplement() === 'FRITE'
                            ? $complement->getFrite()
                            : null,
            'boisson'    => $complement->getTypecomplement() === 'BOISSON'
                            ? $complement->getBoisson()
                            : null,
        ]);
    }

    #[Route('/delete/{id}', name: 'gestionnaire_complement_delete', methods: ['POST'])]
    public function delete(int $id): Response
    {
        $this->complementService->delete($id);
        return $this->redirectToRoute('gestionnaire_complements');
    }
}
