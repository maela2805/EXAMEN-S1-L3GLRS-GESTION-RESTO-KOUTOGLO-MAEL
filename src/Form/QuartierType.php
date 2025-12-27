<?php

namespace App\Form;

use App\Entity\Quartier;
use App\Entity\Zone;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class QuartierType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options):void
    {
        $builder
            ->add('libelle', TextType::class, [
                'label' => 'Nom du quartier'
            ])
            ->add('zone', EntityType::class, [
                'class' => Zone::class,
                'choice_label' => fn (Zone $zone) => 'Zone #' . $zone->getId() . ' - ' . $zone->getTarif() . ' FCFA',
                'label' => 'Zone'
            ]);
    }

    public function configureOptions(OptionsResolver $resolver):void
    {
        $resolver->setDefaults([
            'data_class' => Quartier::class,
        ]);
    }
}
