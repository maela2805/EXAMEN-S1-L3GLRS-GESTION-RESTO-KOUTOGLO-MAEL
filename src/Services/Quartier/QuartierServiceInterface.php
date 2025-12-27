<?php

namespace App\Services\Quartier;

use App\Entity\Quartier;

interface QuartierServiceInterface
{
    public function getAll(): array;

    public function create(Quartier $quartier): void;

    public function find(int $id): ?Quartier;

    public function delete(int $id): void;
}
