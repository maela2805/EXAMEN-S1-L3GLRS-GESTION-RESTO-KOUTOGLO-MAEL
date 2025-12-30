# Image PHP + Apache
FROM php:8.2-apache

# Installer dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    && docker-php-ext-install pdo pdo_pgsql

# Activer mod_rewrite pour Symfony
RUN a2enmod rewrite

# Définir le dossier de travail
WORKDIR /var/www/html

# Copier les fichiers du projet
COPY . .

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Installer les dépendances Symfony (prod)
RUN composer install --no-dev --optimize-autoloader

# Permissions
RUN chown -R www-data:www-data var

# Config Apache → dossier public
RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf

EXPOSE 80
