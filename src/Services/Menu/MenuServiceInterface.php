<?php

namespace App\Services\Menu;

use App\Entity\Menu;
use App\Entity\Product;
use Symfony\Component\HttpFoundation\File\UploadedFile;

interface MenuServiceInterface
{
    public function getAll(): array;
    public function create(Product $product,?string $imagePath=null,int $burgerId,int $friteId,int $boissonId): void;
    public function update(Menu $menu,Product $product,int $burgerId,int $friteId,int $boissonId,?string $imagePath = null): void;
    public function delete(Menu $menu): void;
}
