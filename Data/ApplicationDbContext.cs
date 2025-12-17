using Microsoft.EntityFrameworkCore;
using gestion_restaurant.Models;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }
        public DbSet<User> Users { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<Frite> Frites { get; set; }
        public DbSet<Boisson> Boissons { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<CommandeItem> CommandeItems { get; set; }
        public DbSet<CommandeComplement> CommandeComplements { get; set; }
        public DbSet<CommandeMenuComplement> CommandeMenuComplements { get; set; }
        public DbSet<Livraison> Livraisons { get; set; }
        public DbSet<Livreur> Livreurs { get; set; }
        public DbSet<Zone> Zones { get; set; }
        public DbSet<Quartier> Quartiers { get; set; }
        public DbSet<MenuBurger> MenuBurgers { get; set; }
        public DbSet<MenuComplement> MenuComplements { get; set; }
        public DbSet<Paiement> Paiements { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasPostgresEnum<ComplementType>();
            modelBuilder.HasPostgresEnum<ModePaiement>();
            modelBuilder.HasPostgresEnum<ProductType>();
            modelBuilder.HasPostgresEnum<RoleType>();
            modelBuilder.HasPostgresEnum<StatutCommande>();
            modelBuilder.HasPostgresEnum<LivraisonStatut>();
            modelBuilder.Entity<User>().ToTable("users");
            modelBuilder.Entity<Product>().ToTable("product");
            modelBuilder.Entity<Burger>().ToTable("burger");
            modelBuilder.Entity<Menu>().ToTable("menu");
            modelBuilder.Entity<Complement>().ToTable("complement");
            modelBuilder.Entity<Frite>().ToTable("frite");
            modelBuilder.Entity<Boisson>().ToTable("boisson");
            modelBuilder.Entity<Commande>().ToTable("commande");
            modelBuilder.Entity<CommandeItem>().ToTable("commande_items");
            modelBuilder.Entity<CommandeComplement>().ToTable("commande_complements");
            modelBuilder.Entity<CommandeMenuComplement>().ToTable("commande_menu_complements");
            modelBuilder.Entity<Paiement>().ToTable("paiement");
            modelBuilder.Entity<Livraison>().ToTable("livraison");
            modelBuilder.Entity<Livreur>().ToTable("livreur");
            modelBuilder.Entity<Zone>().ToTable("zone");
            modelBuilder.Entity<Quartier>().ToTable("quartier");
            modelBuilder.Entity<MenuBurger>().ToTable("menu_burger");
            modelBuilder.Entity<MenuComplement>().ToTable("menu_complement");

            modelBuilder.Entity<MenuBurger>()
                .HasIndex(mb => new { mb.MenuId, mb.BurgerId })
                .IsUnique();

            modelBuilder.Entity<MenuComplement>()
                .HasIndex(mc => new { mc.MenuId, mc.ComplementId })
                .IsUnique();

            modelBuilder.Entity<Paiement>()
                .HasIndex(p => p.CommandeId)
                .IsUnique();

            // =======================
            // RELATIONS
            // =======================
            modelBuilder.Entity<Commande>()
                .HasOne(c => c.Client)
                .WithMany()
                .HasForeignKey(c => c.ClientId)
                .OnDelete(DeleteBehavior.SetNull);

            modelBuilder.Entity<Paiement>()
                .HasOne(p => p.Commande)
                .WithOne()
                .HasForeignKey<Paiement>(p => p.CommandeId)
                .OnDelete(DeleteBehavior.Cascade);
        }
    }
}
