using System.ComponentModel.DataAnnotations.Schema;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    [Table("commande")]
    public class Commande
    {
        [Column("id")]
        public long Id { get; set; }

        [Column("client_id")]
        public long? ClientId { get; set; }
        public User Client { get; set; }

        [Column("date_commande")]
        public DateTime DateCommande { get; set; }

        [Column("montant_total")]
        public decimal MontantTotal { get; set; }

        [Column("statut")]
        public StatutCommande Statut { get; set; }

        [Column("is_paye")]
        public bool IsPaye { get; set; }

        [Column("livraison_id")]
        public long? LivraisonId { get; set; }
        public Livraison? Livraison { get; set; }


        public List<CommandeItem> CommandeItems { get; set; } = new();
    }
}
