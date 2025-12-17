namespace gestion_restaurant.Models
{
    public class MenuComplement
    {
        public long Id { get; set; }

        public long MenuId { get; set; }
        public Menu Menu { get; set; }

        public long ComplementId { get; set; }
        public Complement Complement { get; set; }
        public int Quantite { get; set; }
        public string Role { get; set; }
    }
}
