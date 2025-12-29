<?php

namespace App\Form;

use App\Entity\Burger;
use App\Entity\Complement;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Doctrine\ORM\EntityRepository;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;

class MenuType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options):void
    {
        $builder
            ->add('nom')
            ->add('description')
            ->add('imageFile', FileType::class, [
                'mapped' => false,
                'required' => false,
                'constraints' => [
                    new File(
                        maxSize: '2M',
                        mimeTypes: ['image/jpeg','image/png','image/webp'],
                        mimeTypesMessage: 'Image invalide'
                    )
                ]
            ])
            ->add('burger', EntityType::class, [
                'class' => Burger::class,
                'choice_label' => fn (Burger $b) => $b->getProduct()->getNom(),
                'mapped' => false
            ])
            ->add('frite', EntityType::class, [
                'class' => Complement::class,
                'mapped' => false,
                'query_builder' => fn ($er) =>
                    $er->createQueryBuilder('c')
                    ->where("c.typecomplement = 'FRITE'")
            ])
            ->add('boisson', EntityType::class, [
                'class' => Complement::class,
                'mapped' => false,
                'query_builder' => fn ($er) =>
                    $er->createQueryBuilder('c')
                    ->where("c.typecomplement = 'BOISSON'")
            ]);

    }
}


