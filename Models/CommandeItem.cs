using System.ComponentModel.DataAnnotations.Schema;

namespace gestion_restaurant.Models
{
    [Table("commande_items")]
    public class CommandeItem
    {
        public long Id { get; set; }

        [Column("commande_id")]
        public long CommandeId { get; set; }

        [Column("product_id")]
        public long ProductId { get; set; }

        [Column("quantite")]
        public int Quantite { get; set; }

        [Column("prix_unitaire")]
        public decimal PrixUnitaire { get; set; }

        public Commande Commande { get; set; }
        public Product Product { get; set; }
    }
}
