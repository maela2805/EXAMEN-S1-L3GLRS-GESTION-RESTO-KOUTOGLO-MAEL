namespace gestion_restaurant.Models
{
    public class Boisson
    {
        public long Id { get; set; }
        public string Volume { get; set; }
        public Complement Complement { get; set; }
    }
}
