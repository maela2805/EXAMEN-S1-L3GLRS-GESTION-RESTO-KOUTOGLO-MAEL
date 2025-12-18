namespace gestion_restaurant.Models
{
    public class CartItem
    {
        public long ProductId { get; set; }
        public string Nom { get; set; }
        public string Image { get; set; }

        public int Quantite { get; set; } = 1;

        public decimal PrixUnitaire { get; set; }   
        public decimal Total => PrixUnitaire * Quantite;

        public string? Frite { get; set; }
        public decimal? PrixFrite { get; set; }

        public string? Boisson { get; set; }
        public decimal? PrixBoisson { get; set; }
    }
}
