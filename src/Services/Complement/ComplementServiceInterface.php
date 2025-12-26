<?php
namespace App\Services\Complement;

use App\Entity\Complement;

interface ComplementServiceInterface
{
    public function getAll(): array;
    public function create(Complement $complement, ?string $imagePath, array $extra): void;
    public function update(int $id, array $data, ?string $imagePath): void;
    public function delete(int $id): void;
    public function find(int $id): ?Complement;
}
