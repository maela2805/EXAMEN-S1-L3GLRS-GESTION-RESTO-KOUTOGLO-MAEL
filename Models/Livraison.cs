using gestion_restaurant.Models.Enums;
using System.ComponentModel.DataAnnotations.Schema;

namespace gestion_restaurant.Models
{
    [Table("livraison")]
    public class Livraison
    {
        public long Id { get; set; }

        [Column("commande_id")]
        public long CommandeId { get; set; }

        [Column("livreur_id")]
        public long? LivreurId { get; set; }

        [Column("date_affectation")]
        public DateTime DateAffectation { get; set; }

        [Column("date_livraison")]
        public DateTime? DateLivraison { get; set; }

        [Column("statut")]
        public LivraisonStatut Statut { get; set; }

        public Commande Commande { get; set; }
    }
}
