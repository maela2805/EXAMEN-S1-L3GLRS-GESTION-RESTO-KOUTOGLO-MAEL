<?php

namespace App\Form;

use App\Entity\Zone;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ZoneType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('tarif', IntegerType::class, [
            'label' => 'Tarif de livraison (FCFA)'
        ]);
    }

    public function configureOptions(OptionsResolver $resolver):void
    {
        $resolver->setDefaults([
            'data_class' => Zone::class,
        ]);
    }
}
