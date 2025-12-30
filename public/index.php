<?php

// use App\Kernel;

// $_SERVER['APP_ENV'] = 'dev';
// $_SERVER['APP_DEBUG'] = true;

// require dirname(__DIR__).'/vendor/autoload_runtime.php';

// return function (array $context) {
//     return new Kernel('dev', true);
// };

// use App\Kernel;

// require_once dirname(__DIR__).'/vendor/autoload_runtime.php';

// return function (array $context) {
//     return new Kernel(
//         $_SERVER['APP_ENV'] ?? 'prod',
//         (bool) ($_SERVER['APP_DEBUG'] ?? false)
//     );
// };

// public/index.php

// Solution rapide - DÉCOMMENTEZ/REMPLACEZ ces lignes :
// Au lieu de :
// require_once dirname(__DIR__).'/vendor/autoload_runtime.php';
// return function (array $context) {
//     return new Kernel($context['APP_ENV'], (bool) $context['APP_DEBUG']);
// };

// UTILISEZ CE CODE À LA PLACE :
use App\Kernel;
use Symfony\Component\Dotenv\Dotenv;

// Définir APP_ENV avant tout
$_SERVER['APP_ENV'] = $_ENV['APP_ENV'] = $_SERVER['APP_ENV'] ?? $_ENV['APP_ENV'] ?? 'prod';

// Si le fichier .env n'existe pas, ne pas essayer de le charger
if (file_exists(dirname(__DIR__).'/.env')) {
    (new Dotenv())->bootEnv(dirname(__DIR__).'/.env');
}

// Définir le mode debug en fonction de l'environnement
$_SERVER['APP_DEBUG'] = $_SERVER['APP_DEBUG'] ?? $_ENV['APP_DEBUG'] ?? 'prod' !== $_SERVER['APP_ENV'];
$_SERVER['APP_DEBUG'] = $_ENV['APP_DEBUG'] = (int) $_SERVER['APP_DEBUG'] || filter_var($_SERVER['APP_DEBUG'], FILTER_VALIDATE_BOOLEAN) ? '1' : '0';

require_once dirname(__DIR__).'/vendor/autoload_runtime.php';

return function (array $context) {
    return new Kernel($context['APP_ENV'], (bool) $context['APP_DEBUG']);
};
