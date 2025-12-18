using Npgsql;
using Microsoft.EntityFrameworkCore;
using gestion_restaurant.Data;
using gestion_restaurant.Models.Enums;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllersWithViews();

var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

var dataSourceBuilder = new NpgsqlDataSourceBuilder(connectionString);

dataSourceBuilder.MapEnum<ProductType>("public.product_type");
dataSourceBuilder.MapEnum<ComplementType>("public.complement_type");
dataSourceBuilder.MapEnum<ModePaiement>("public.mode_paiement");
dataSourceBuilder.MapEnum<RoleType>("public.role_type");
dataSourceBuilder.MapEnum<StatutCommande>("public.statut_commande");
dataSourceBuilder.MapEnum<LivraisonStatut>("public.livraison_statut");

var dataSource = dataSourceBuilder.Build();

builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(dataSource)
);

var app = builder.Build();

app.UseStaticFiles();
app.UseRouting();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Client}/{action=Accueil}/{id?}");

app.Run();
