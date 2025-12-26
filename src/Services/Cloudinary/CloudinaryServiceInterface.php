<?php

namespace App\Services\Cloudinary;

interface CloudinaryServiceInterface
{
    public function upload(string $filePath): array;
    public function delete(?string $publicId): void;
}
