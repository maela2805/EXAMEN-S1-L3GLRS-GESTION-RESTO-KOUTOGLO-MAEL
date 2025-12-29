<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Menu;
use App\Entity\Product;
use App\Form\MenuType;
use App\Services\Menu\MenuServiceInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/gestionnaire/menus')]
class MenuController extends AbstractController
{
    #[Route('', name: 'gestionnaire_menus')]
    public function index(MenuServiceInterface $menuService): Response
    {
        return $this->render('gestionnaire/menu/index.html.twig', [
            'menus' => $menuService->getAll()
        ]);
    }

    #[Route('/new', name: 'gestionnaire_menu_new')]
    public function new(Request $request,MenuServiceInterface $menuService): Response {
        $product = new Product();

        $form = $this->createForm(MenuType::class, $product);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            $imagePath = $imageFile ? $imageFile->getPathname() : null;
            $menuService->create(
                $product,
                $imagePath,
                $form->get('burger')->getData()->getId(),
                $form->get('frite')->getData()->getId(),
                $form->get('boisson')->getData()->getId()
            );

            return $this->redirectToRoute('gestionnaire_menus');
        }

        return $this->render('gestionnaire/menu/new.html.twig', [
            'form' => $form->createView()
        ]);
    }


    #[Route('/edit/{id}', name: 'gestionnaire_menu_edit')]
    public function edit(Menu $menu,Request $request,MenuServiceInterface $menuService): Response {
        $product = $menu->getProduct();
        $form = $this->createForm(MenuType::class, $product);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form->get('imageFile')->getData();
            $imagePath = null;

            if ($imageFile) {
                $imagePath = $imageFile->getPathname();
            }

            $menuService->update(
                $menu,
                $product,
                $form->get('burger')->getData()->getId(),
                $form->get('frite')->getData()->getId(),
                $form->get('boisson')->getData()->getId(),
                $imagePath
            );

            return $this->redirectToRoute('gestionnaire_menus');
        }

        return $this->render('gestionnaire/menu/edit.html.twig', [
            'form' => $form->createView(),
            'menu' => $menu
        ]);
    }

    #[Route('/delete/{id}', name: 'gestionnaire_menu_delete', methods: ['POST'])]
    public function delete(
        Menu $menu,
        MenuServiceInterface $menuService
    ): Response {
        $menuService->delete($menu);

        return $this->redirectToRoute('gestionnaire_menus');
    }
}
