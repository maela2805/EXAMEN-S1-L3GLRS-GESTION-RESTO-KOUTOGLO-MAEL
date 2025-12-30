FROM php:8.4-apache

# Dépendances système
RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    && docker-php-ext-install pdo pdo_pgsql

# Apache
RUN a2enmod rewrite

# Dossier de travail
WORKDIR /var/www/html

# Copier le projet
COPY . .

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# ⚠️ IMPORTANT : pas de cache:clear ici
RUN composer install --no-dev --optimize-autoloader --no-interaction --no-scripts

# Permissions
RUN mkdir -p var/cache var/log \
    && chown -R www-data:www-data var

# Apache → public/
RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf \
 && sed -i 's/AllowOverride None/AllowOverride All/g' \
    /etc/apache2/apache2.conf

EXPOSE 80
