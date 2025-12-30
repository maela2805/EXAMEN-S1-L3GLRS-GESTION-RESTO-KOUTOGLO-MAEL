FROM php:8.4-apache

# Dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

# Activer mod_rewrite
RUN a2enmod rewrite

# Config Apache pour Symfony
RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf

WORKDIR /var/www/html

# Copier le projet
COPY . .

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# .env vide obligatoire pour Symfony
RUN touch .env

# Installer dépendances
RUN composer install --no-dev --optimize-autoloader --no-interaction

# Dossiers Symfony
RUN mkdir -p var/cache var/log
RUN chown -R www-data:www-data var

EXPOSE 80
