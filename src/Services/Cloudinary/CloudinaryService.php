<?php

namespace App\Services\Cloudinary;

use Cloudinary\Cloudinary;

class CloudinaryService implements CloudinaryServiceInterface
{
    private Cloudinary $cloudinary;

    public function __construct()
    {
        $this->cloudinary = new Cloudinary($_ENV['CLOUDINARY_URL']);
    }

    public function upload(string $filePath): array
    {
        return (array) $this->cloudinary->uploadApi()->upload($filePath, [
            'folder' => 'gestion_restaurant/burgers'
        ]);
    }

    public function delete(?string $publicId): void
    {
        if ($publicId) {
            $this->cloudinary->uploadApi()->destroy($publicId);
        }
    }
}
