<?php

use Symfony\Component\Dotenv\Dotenv;

require dirname(__DIR__).'/vendor/autoload.php';

// Charger .env UNIQUEMENT si on est en local
if (!isset($_ENV['APP_ENV'])) {
    (new Dotenv())->bootEnv(dirname(__DIR__).'/.env');
}
