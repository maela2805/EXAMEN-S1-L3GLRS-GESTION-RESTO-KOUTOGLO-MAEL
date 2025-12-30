# PHP 8.4 + Apache
FROM php:8.4-apache

# Dépendances système
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

# Apache
RUN a2enmod rewrite

WORKDIR /var/www/html

# Copier le projet
COPY . .

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# ⚠️ IMPORTANT : pas de scripts Symfony au build
RUN composer install --no-dev --optimize-autoloader --no-interaction --no-scripts

# Dossiers Symfony
RUN mkdir -p var/cache var/log && chown -R www-data:www-data var

# Apache config Symfony
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
