namespace gestion_restaurant.Models
{
    public class CommandeItem
    {
        public long Id { get; set; }
        public long? CommandeId { get; set; }
        public Commande Commande { get; set; }
        public long? ProductId { get; set; }
        public Product Product { get; set; }
        public int Quantite { get; set; }
        public decimal PrixUnitaire { get; set; }
        public string Remarque { get; set; }
    }
}
