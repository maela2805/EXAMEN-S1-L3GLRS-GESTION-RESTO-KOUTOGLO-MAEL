using gestion_restaurant.Models;

namespace gestion_restaurant.ViewModels
{
    public class ProductDetailsViewModel
    {
        public Product Product { get; set; }

        public List<Complement> Frites { get; set; } = new();
        public List<Complement> Boissons { get; set; } = new();
    }
}
