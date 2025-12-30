FROM php:8.4-apache

RUN apt-get update && apt-get install -y \
    git unzip libpq-dev \
    && docker-php-ext-install pdo pdo_pgsql

RUN a2enmod rewrite

WORKDIR /var/www/html

COPY . .

# SOLUTION UNIQUE : Créer .env minimal
RUN echo "APP_ENV=prod" > .env && echo "APP_DEBUG=0" >> .env

RUN mkdir -p var/cache var/log \
    && chmod -R 777 var

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

RUN composer install --no-dev --optimize-autoloader --no-interaction

RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf \
 && sed -i 's/AllowOverride None/AllowOverride All/g' \
    /etc/apache2/apache2.conf

EXPOSE 80

CMD ["apache2-foreground"]