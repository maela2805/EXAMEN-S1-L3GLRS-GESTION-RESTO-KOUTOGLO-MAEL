# PHP 8.4 + Apache
FROM php:8.4-apache

# Dépendances système (IMPORTANT : libicu-dev)
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

# Activer mod_rewrite
RUN a2enmod rewrite

# Dossier de travail
WORKDIR /var/www/html

# Copier le projet
COPY . .

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Installer dépendances Symfony (prod)
RUN composer install --no-dev --optimize-autoloader --no-interaction

# Dossiers Symfony
RUN mkdir -p var/cache var/log && chown -R www-data:www-data var

# Configuration Apache pour Symfony
RUN printf "<VirtualHost *:80>\n\
    ServerName localhost\n\
    DocumentRoot /var/www/html/public\n\
    <Directory /var/www/html/public>\n\
        AllowOverride All\n\
        Require all granted\n\
        FallbackResource /index.php\n\
    </Directory>\n\
</VirtualHost>" > /etc/apache2/sites-available/000-default.conf

EXPOSE 80
