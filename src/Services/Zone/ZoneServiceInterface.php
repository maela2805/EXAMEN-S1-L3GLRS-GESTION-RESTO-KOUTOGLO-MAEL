<?php

namespace App\Services\Zone;

use App\Entity\Zone;

interface ZoneServiceInterface
{
    public function getAll(): array;

    public function create(Zone $zone): void;

    public function find(int $id): ?Zone;

    public function delete(int $id): void;
}
