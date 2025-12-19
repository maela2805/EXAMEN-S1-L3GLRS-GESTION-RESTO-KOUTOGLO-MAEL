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

builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30);
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

var port = Environment.GetEnvironmentVariable("PORT") ?? "10000";
builder.WebHost.UseUrls($"http://0.0.0.0:{port}");
var app = builder.Build();
app.UseStaticFiles();
app.UseRouting();
app.UseSession();


app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Auth}/{action=Login}/{id?}");

app.Run();
