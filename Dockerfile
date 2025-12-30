# PHP + Apache
FROM php:8.2-apache

# Dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    && docker-php-ext-install pdo pdo_pgsql

# Apache rewrite
RUN a2enmod rewrite

# Dossier de travail
WORKDIR /var/www/html

# Copier le projet
COPY . .

# Créer un .env vide (obligatoire au build)
RUN touch .env

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Installer dépendances PROD sans scripts
RUN composer install \
    --no-dev \
    --optimize-autoloader \
    --no-scripts

# Créer dossiers Symfony
RUN mkdir -p var/cache var/log

# Permissions
RUN chown -R www-data:www-data var

# Apache → public/
RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf

EXPOSE 80
