using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using gestion_restaurant.Data;
using gestion_restaurant.ViewModels;
using gestion_restaurant.Helpers;
using gestion_restaurant.Models;

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

        [HttpPost]
        public async Task<IActionResult> AddToCart(
            long productId,
            long? friteId,
            long? boissonId)
        {
            var product = await _context.Products.FindAsync(productId);
            if (product == null)
                return NotFound();

            decimal total = product.Prix;

            string? friteNom = null;
            decimal? fritePrix = null;

            string? boissonNom = null;
            decimal? boissonPrix = null;

            if (friteId.HasValue)
            {
                var frite = await _context.Complements.FindAsync(friteId);
                if (frite != null)
                {
                    friteNom = frite.Nom;
                    fritePrix = frite.Prix;
                    total += frite.Prix;
                }
            }

            if (boissonId.HasValue)
            {
                var boisson = await _context.Complements.FindAsync(boissonId);
                if (boisson != null)
                {
                    boissonNom = boisson.Nom;
                    boissonPrix = boisson.Prix;
                    total += boisson.Prix;
                }
            }

            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART")
                    ?? new List<CartItem>();

            cart.Add(new CartItem
            {
                ProductId = product.Id,
                Nom = product.Nom,
                Image = product.Image,
                PrixUnitaire = total,
                Frite = friteNom,
                PrixFrite = fritePrix,
                Boisson = boissonNom,
                PrixBoisson = boissonPrix
            });

            HttpContext.Session.SetObjectAsJson("CART", cart);

            return RedirectToAction("Panier");
        }

        public IActionResult Panier()
        {
            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART")
                    ?? new List<CartItem>();

            return View(cart);
        }

    }





    
}
