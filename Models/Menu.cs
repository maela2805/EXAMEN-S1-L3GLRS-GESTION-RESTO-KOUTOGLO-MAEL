namespace gestion_restaurant.Models
{
    public class Menu
    {
        public long Id { get; set; }
        public string Description { get; set; }
        public Product Product { get; set; }
    }
}
