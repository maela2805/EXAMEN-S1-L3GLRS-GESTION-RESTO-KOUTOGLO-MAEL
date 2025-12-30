<?php

use Symfony\Component\Dotenv\Dotenv;

if (!isset($_SERVER['APP_ENV']) && !isset($_ENV['APP_ENV'])) {
    (new Dotenv())->bootEnv(dirname(__DIR__).'/.env');
}
