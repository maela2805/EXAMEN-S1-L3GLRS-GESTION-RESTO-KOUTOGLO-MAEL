<?php

namespace App\Services\Complement;

use App\Entity\Complement;
use App\Entity\Frite;
use App\Entity\Boisson;
use App\Services\Cloudinary\CloudinaryServiceInterface;
use Doctrine\ORM\EntityManagerInterface;

class ComplementService implements ComplementServiceInterface
{
    public function __construct(
        private EntityManagerInterface $em,
        private CloudinaryServiceInterface $cloudinary
    ) {}

    public function getAll(): array
    {
        return $this->em->getRepository(Complement::class)->findAll();
    }

    public function create(Complement $c, ?string $imagePath, array $extra): void
    {
        if ($imagePath) {
            $upload = $this->cloudinary->upload($imagePath);
            $c->setImage($upload['secure_url']);
            $c->setImagePublicId($upload['public_id']);
        }

        $this->em->persist($c);
        $this->em->flush();

        if ($c->getTypecomplement() === 'FRITE') {
            $frite = new Frite();
            $frite->setComplement($c);
            $frite->setTaille($extra['taille']);
            $this->em->persist($frite);
        }

        if ($c->getTypecomplement() === 'BOISSON') {
            $boisson = new Boisson();
            $boisson->setComplement($c);
            $boisson->setVolume($extra['volume']);
            $this->em->persist($boisson);
        }

        $this->em->flush();
    }

    public function update(int $id, array $data, ?string $imagePath): void
    {
        $c = $this->em->getRepository(Complement::class)->find($id);

        if (!$c) {
            throw new \RuntimeException('Complément introuvable');
        }

        $c->setNom($data['nom'])
        ->setPrix($data['prix']);

        if (isset($data['description'])) {
            $c->setDescription($data['description']);
        }


        if ($imagePath) {
            if ($c->getImagePublicId()) {
                $this->cloudinary->delete($c->getImagePublicId());
            }

            $upload = $this->cloudinary->upload($imagePath);
            $c->setImage($upload['secure_url']);
            $c->setImagePublicId($upload['public_id']);
        }
        if ($c->getTypecomplement() === 'FRITE') {
            $frite = $this->em->getRepository(Frite::class)->find($id);
            if ($frite) {
                $frite->setTaille($data['taille']);
            }
        }
        if ($c->getTypecomplement() === 'BOISSON') {
            $boisson = $this->em->getRepository(Boisson::class)->find($id);
            if ($boisson) {
                $boisson->setVolume($data['volume']);
            }
        }

        $this->em->flush();
    }


    public function delete(int $id): void
    {
        $c = $this->em->getRepository(Complement::class)->find($id);
        if (!$c) return;

        if ($c->getImagePublicId()) {
            $this->cloudinary->delete($c->getImagePublicId());
        }

        $this->em->remove($c);
        $this->em->flush();
    }

    public function find(int $id): ?Complement
    {
        return $this->em->getRepository(Complement::class)->find($id);
    }

}
