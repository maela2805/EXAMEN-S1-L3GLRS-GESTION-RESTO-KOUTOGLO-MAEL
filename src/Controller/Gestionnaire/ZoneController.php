<?php

namespace App\Controller\Gestionnaire;

use App\Entity\Zone;
use App\Form\ZoneType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\HttpFoundation\Response;

#[Route('/gestionnaire/zones')]
class ZoneController extends AbstractController
{
    #[Route('', name: 'gestionnaire_zones')]
    public function index(EntityManagerInterface $em): Response
    {
        return $this->render('gestionnaire/zone/index.html.twig', [
            'zones' => $em->getRepository(Zone::class)->findAll()
        ]);
    }

    #[Route('/new', name: 'gestionnaire_zone_new')]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $zone = new Zone();
        $form = $this->createForm(ZoneType::class, $zone);

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($zone);
            $em->flush();

            return $this->redirectToRoute('gestionnaire_zones');
        }

        return $this->render('gestionnaire/zone/new.html.twig', [
            'form' => $form->createView()
        ]);
    }

    #[Route('/edit/{id}', name: 'gestionnaire_zone_edit')]
    public function edit(Zone $zone,Request $request,EntityManagerInterface $em): Response {
        $form = $this->createForm(ZoneType::class, $zone);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();

            return $this->redirectToRoute('gestionnaire_zones');
        }

        return $this->render('gestionnaire/zone/edit.html.twig', [
            'zone' => $zone,
            'form' => $form->createView(), 
        ]);
    }

    #[Route('/delete/{id}', name: 'gestionnaire_zone_delete', methods: ['POST'])]
    public function delete(int $id, EntityManagerInterface $em): Response
    {
        $zone = $em->getRepository(Zone::class)->find($id);

        if ($zone) {
            $em->remove($zone);
            $em->flush();
        }

        return $this->redirectToRoute('gestionnaire_zones');
    }
}
