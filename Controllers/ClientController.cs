using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using gestion_restaurant.Data;
using gestion_restaurant.ViewModels;

namespace gestion_restaurant.Controllers
{
    public class ClientController : Controller
    {
        private readonly ApplicationDbContext _context;

        public ClientController(ApplicationDbContext context)
        {
            _context = context;
        }

        // Page d'accueil
        public IActionResult Accueil()
        {
            return View();
        }

        // Catalogue
        public async Task<IActionResult> Index()
        {
            var burgers = await _context.Products
                .Where(p => p.TypeProduit == "BURGER")
                .ToListAsync();

            var menus = await _context.Products
                .Where(p => p.TypeProduit == "MENU")
                .ToListAsync();

            var complements = await _context.Complements
                .ToListAsync();

            var model = new ClientCatalogueViewModel
            {
                Burgers = burgers,
                Menus = menus,
                Complements = complements
            };

            return View(model);
        }
    }
}
