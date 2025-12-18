using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace gestion_restaurant.Models
{
    [Table("complement")]
    public class Complement
    {
        [Key]
        [Column("id")]
        public long Id { get; set; }

        [Column("nom")]
        public string Nom { get; set; }

        [Column("image")]
        public string Image { get; set; }

        [Column("prix")]
        public decimal Prix { get; set; }

        [Column("typecomplement")]
        public string TypeComplement { get; set; }

        [Column("image_public_id")]
        public string ImagePublicId { get; set; }

        [Column("description")]
        public string Description { get; set; }
    }
}
