<?php

namespace App\Form;

use App\Entity\Complement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\MoneyType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ComplementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', TextType::class, [
                'label' => 'Nom du complément'
            ])

            ->add('prix', MoneyType::class, [
                'label' => 'Prix (FCFA)',
                'currency' => 'XOF'
            ])

            ->add('typecomplement', ChoiceType::class, [
                'label' => 'Type',
                'choices' => [
                    'Frite' => 'FRITE',
                    'Boisson' => 'BOISSON'
                ]
            ])

            // Champs dynamiques (non mappés)
            ->add('taille', TextType::class, [
                'label' => 'Taille (ex: Petite, Moyenne)',
                'mapped' => false,
                'required' => false
            ])

            ->add('volume', TextType::class, [
                'label' => 'Volume (ex: 33cl, 50cl)',
                'mapped' => false,
                'required' => false
            ])

            ->add('imageFile', FileType::class, [
                'label' => 'Image',
                'mapped' => false,
                'required' => false
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Complement::class,
        ]);
    }
}
