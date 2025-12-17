namespace gestion_restaurant.Models
{
    public class CommandeComplement
    {
        public long Id { get; set; }

        public long? CommandeId { get; set; }
        public Commande Commande { get; set; }

        public long? ComplementId { get; set; }
        public Complement Complement { get; set; }

        public int Quantite { get; set; }
        public decimal? PrixUnitaire { get; set; }
    }
}
