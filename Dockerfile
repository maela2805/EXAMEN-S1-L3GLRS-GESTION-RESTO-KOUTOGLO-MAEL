FROM php:8.4-apache

RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

RUN a2enmod rewrite

RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf

WORKDIR /var/www/html

COPY . .

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Variables PROD obligatoires
ENV APP_ENV=prod
ENV APP_DEBUG=0

# .env vide (Symfony l’exige)
RUN touch .env

RUN composer install --no-dev --optimize-autoloader --no-interaction

RUN mkdir -p var/cache var/log
RUN chown -R www-data:www-data var

EXPOSE 80
