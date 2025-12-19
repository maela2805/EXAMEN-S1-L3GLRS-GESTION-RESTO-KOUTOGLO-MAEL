using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using gestion_restaurant.Models;

[Table("zone")]
public class Zone
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("tarif")]
    public decimal Tarif { get; set; }

    public ICollection<Quartier> Quartiers { get; set; }
}
