using gestion_restaurant.Models;
using System.Collections.Generic;

namespace gestion_restaurant.ViewModels
{
    public class CheckoutViewModel
    {
        public List<CartItem> Cart { get; set; } = new();
        public List<Quartier> Quartiers { get; set; } = new();
    }
}
