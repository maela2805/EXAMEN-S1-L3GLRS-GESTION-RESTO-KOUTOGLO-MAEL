<?php

namespace App\Services\Livreur;

interface LivreurServiceInterface
{
    public function create(array $data): void;
    public function getAll(): array;
}
