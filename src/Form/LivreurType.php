<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;

class LivreurType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options):void
    {
        $builder
            ->add('nom')
            ->add('prenom')
            ->add('telephone')
            ->add('login')
            ->add('password')
            ->add('matricule_moto');
    }
}
