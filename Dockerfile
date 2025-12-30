# PHP 8.4 + Apache
FROM php:8.4-apache

# Dépendances système nécessaires à Symfony + PostgreSQL
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    && docker-php-ext-install pdo pdo_pgsql

# Activer mod_rewrite (Symfony)
RUN a2enmod rewrite

# Dossier de travail
WORKDIR /var/www/html

# Copier le projet
COPY . .

# ⚠️ Créer un .env MINIMAL pour permettre le build Symfony
RUN echo "APP_ENV=prod" > .env \
 && echo "APP_SECRET=dummy_secret" >> .env

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Installer dépendances PROD
RUN composer install --no-dev --optimize-autoloader --no-interaction

# Supprimer le .env temporaire (Render injecte les vraies variables)
RUN rm .env

# Dossiers Symfony
RUN mkdir -p var/cache var/log

# Permissions
RUN chown -R www-data:www-data var

# Apache → dossier public Symfony
RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf

EXPOSE 80
