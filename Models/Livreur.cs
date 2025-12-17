namespace gestion_restaurant.Models
{
    public class Livreur
    {
        public long Id { get; set; }
        public string MatriculeMoto { get; set; }
        public bool Disponible { get; set; }
        public User User { get; set; }
    }
}
