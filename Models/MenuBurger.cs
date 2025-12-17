namespace gestion_restaurant.Models
{
    public class MenuBurger
    {
        public long Id { get; set; }

        public long MenuId { get; set; }
        public Menu Menu { get; set; }

        public long BurgerId { get; set; }
        public Burger Burger { get; set; }

        public int Quantite { get; set; }
    }
}
