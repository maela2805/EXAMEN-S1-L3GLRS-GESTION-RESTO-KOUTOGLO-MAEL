using gestion_restaurant.Models;
namespace gestion_restaurant.ViewModels
{
    public class ClientCatalogueViewModel
    {
        public List<Product> Burgers { get; set; }
        public List<Product> Menus { get; set; }
        public List<Complement> Complements { get; set; }
    }
}


