<?php

namespace App\Services\Burger;

use App\Entity\Burger;
use App\Entity\Product;

interface BurgerServiceInterface
{
    public function getAll(): array;
    public function create(Product $product, ?string $imagePath = null): void;
    public function update(int $id, array $data, ?string $imagePath): void;
    public function delete(int $id): void;
    public function find(int $id): ?Burger;
}
