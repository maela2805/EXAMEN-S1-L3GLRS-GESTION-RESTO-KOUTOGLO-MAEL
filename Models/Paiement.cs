using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    [Table("paiement")]
    public class Paiement
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Column("commande_id")]
        public long CommandeId { get; set; }

        [Column("montant")]
        public decimal Montant { get; set; }

        [Column("date_paiement")]
        public DateTime DatePaiement { get; set; }   

        [Column("mode_paiement")]
        public ModePaiement ModePaiement { get; set; }

        public Commande Commande { get; set; }
    }
}
