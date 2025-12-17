namespace gestion_restaurant.Models
{
    public class CommandeMenuComplement
    {
        public long Id { get; set; }

        public long? CommandeItemId { get; set; }
        public CommandeItem CommandeItem { get; set; }

        public long? ComplementId { get; set; }
        public Complement Complement { get; set; }

        public int Quantite { get; set; }
        public decimal? PrixUnitaire { get; set; }
        public string Remarque { get; set; }
    }
}
