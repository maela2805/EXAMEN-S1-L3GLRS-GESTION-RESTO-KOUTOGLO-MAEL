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

        public IActionResult Accueil()
        {
            return View();
        }

        public async Task<IActionResult> Index(string filter = "all", string search = "")
        {
            search = search?.ToUpper() ?? "";

            var productsQuery = _context.Products.AsQueryable();

            if (!string.IsNullOrEmpty(search))
            {
                productsQuery = productsQuery.Where(p =>
                    p.Nom.ToUpper().Contains(search) ||
                    p.Description.ToUpper().Contains(search));
            }

            var model = new ClientCatalogueViewModel
            {
                ActiveFilter = filter
            };

            if (filter == "all" || filter == "burgers")
                model.Burgers = await productsQuery
                    .Where(p => p.TypeProduit == "BURGER")
                    .ToListAsync();

            if (filter == "all" || filter == "menus")
                model.Menus = await productsQuery
                    .Where(p => p.TypeProduit == "MENU")
                    .ToListAsync();

            if (filter == "all" || filter == "complements")
                model.Complements = await _context.Complements
                    .Where(c => c.Nom.ToUpper().Contains(search))
                    .ToListAsync();

            return View(model);
        }

        public async Task<IActionResult> Details(long id)
{
    var product = await _context.Products
        .FirstOrDefaultAsync(p => p.Id == id);

    if (product == null)
        return NotFound();

    var model = new ProductDetailsViewModel
    {
        Product = product
    };

    if (product.TypeProduit == "BURGER")
    {
        model.Frites = await _context.Complements
            .Where(c => c.TypeComplement == "FRITE")
            .ToListAsync();

        model.Boissons = await _context.Complements
            .Where(c => c.TypeComplement == "BOISSON")
            .ToListAsync();
    }

    return View(model);
}



    }
}
