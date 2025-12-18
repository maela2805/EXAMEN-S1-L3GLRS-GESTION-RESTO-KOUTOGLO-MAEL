using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    [Table("product")]
    public class Product
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Required]
        [Column("nom")]
        public required string Nom { get; set; }

        [Required]
        [Column("image")]
        public required string Image { get; set; }

        [Required]
        [Column("prix")]
        public decimal Prix { get; set; }

        [Required]
        [Column("typeproduit")]
        public required string TypeProduit { get; set; }


        [Required]
        [Column("image_public_id")]
        public required string ImagePublicId { get; set; }

        [Required]
        [Column("description")]
        public required string Description { get; set; }
    }
}
