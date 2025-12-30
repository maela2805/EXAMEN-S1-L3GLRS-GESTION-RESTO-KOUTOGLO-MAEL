<?php

use App\Kernel;

$_SERVER['APP_ENV'] = 'dev';
$_SERVER['APP_DEBUG'] = true;

require dirname(__DIR__).'/vendor/autoload_runtime.php';

return function (array $context) {
    return new Kernel('dev', true);
};
