<?php

use Symfony\Component\Dotenv\Dotenv;

require dirname(__DIR__).'/vendor/autoload.php';

/*
 |------------------------------------------------------------
 | ⚠️ IMPORTANT
 |------------------------------------------------------------
 | En PROD (Render), on NE charge PAS le fichier .env
 | Les variables viennent de Render (Environment Variables)
 */
if ($_SERVER['APP_ENV'] ?? 'prod' !== 'prod') {
    (new Dotenv())->loadEnv(dirname(__DIR__).'/.env');
}
