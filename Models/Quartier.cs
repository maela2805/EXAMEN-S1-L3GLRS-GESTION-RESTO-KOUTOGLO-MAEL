namespace gestion_restaurant.Models
{
    public class Quartier
    {
        public int Id { get; set; }
        public string Libelle { get; set; }
        public int? ZoneId { get; set; }
        public Zone Zone { get; set; }
    }
}
