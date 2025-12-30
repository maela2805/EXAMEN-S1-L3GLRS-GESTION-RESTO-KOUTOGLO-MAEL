FROM php:8.4-apache

RUN apt-get update && apt-get install -y \
    git unzip libpq-dev libicu-dev \
    && docker-php-ext-install pdo pdo_pgsql intl

RUN a2enmod rewrite

WORKDIR /var/www/html

COPY . .

# Créer .env minimal pour le build
RUN echo "APP_ENV=prod" > .env \
 && echo "APP_DEBUG=0" >> .env \
 && echo "DATABASE_URL=sqlite:///%kernel.project_dir%/var/data.db" >> .env

# Créer le fichier SQLite pour éviter l'erreur
RUN touch /var/www/html/var/data.db

RUN mkdir -p var/cache var/log \
    && chmod -R 777 var

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Installation sans les scripts problématiques
RUN composer install --no-dev --optimize-autoloader --no-interaction --no-scripts

# Puis exécuter cache:clear manuellement avec notre .env temporaire
RUN php bin/console cache:clear --env=prod --no-debug --no-warmup

RUN sed -i 's|/var/www/html|/var/www/html/public|g' \
    /etc/apache2/sites-available/000-default.conf \
 && sed -i 's/AllowOverride None/AllowOverride All/g' \
    /etc/apache2/apache2.conf

EXPOSE 80

CMD ["apache2-foreground"]