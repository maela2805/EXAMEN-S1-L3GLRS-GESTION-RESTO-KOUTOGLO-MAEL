<?php

// use App\Kernel;

// require_once dirname(__DIR__).'/vendor/autoload_runtime.php';

// return function (array $context) {
//     return new Kernel($context['APP_ENV'], (bool) $context['APP_DEBUG']);
// }; 
// 🔑 Forcer l'environnement PROD si non défini (Render)
use App\Kernel;

$_SERVER['APP_ENV'] = $_ENV['APP_ENV'] ?? 'prod';
$_SERVER['APP_DEBUG'] = $_ENV['APP_DEBUG'] ?? '0';

require_once dirname(__DIR__).'/vendor/autoload_runtime.php';

return function (array $context) {
    return new Kernel(
        $context['APP_ENV'],
        (bool) $context['APP_DEBUG']
    );
};