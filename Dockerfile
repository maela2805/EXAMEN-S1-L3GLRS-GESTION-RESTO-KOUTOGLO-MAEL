# Image officielle .NET 8
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /app

# Copier les fichiers
COPY . ./

# Restaurer et publier
RUN dotnet restore
RUN dotnet publish -c Release -o out

# Image runtime
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=build /app/out .

# Render utilise la variable PORT
ENV ASPNETCORE_URLS=http://0.0.0.0:${PORT}

EXPOSE 10000
ENTRYPOINT ["dotnet", "gestion_restaurant.dll"]
