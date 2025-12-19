using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace gestion_restaurant.Models
{
    [Table("quartier")]
    public class Quartier
    {
        [Key]
        [Column("id")]
        public int Id { get; set; }

        [Column("libelle")]
        public string Libelle { get; set; }

        [Column("zone_id")]
        public int ZoneId { get; set; }

        public Zone Zone { get; set; }
    }
}
