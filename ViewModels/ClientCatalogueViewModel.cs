using gestion_restaurant.Models;

namespace gestion_restaurant.ViewModels
{
    public class ClientCatalogueViewModel
    {
        public List<Product> Burgers { get; set; } = new();
        public List<Product> Menus { get; set; } = new();
        public List<Complement> Complements { get; set; } = new();

        public string ActiveFilter { get; set; } = "all";
        public string? Search { get; set; }
    }

}
