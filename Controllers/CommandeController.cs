using gestion_restaurant.Data;
using gestion_restaurant.Helpers;
using gestion_restaurant.Models;
using gestion_restaurant.Models.Enums;
using gestion_restaurant.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace gestion_restaurant.Controllers
{
    public class CommandeController : Controller
    {
        private readonly ApplicationDbContext _context;
        private string modePaiement;

        public CommandeController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Checkout()
        {
            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART");

            if (cart == null || !cart.Any())
                return RedirectToAction("Index", "Client");

            var model = new CheckoutViewModel
            {
                Cart = cart,
                Quartiers = _context.Quartiers
                    .OrderBy(q => q.Libelle)
                    .ToList()
            };

            return View(model);
        }


        [HttpPost]
        public async Task<IActionResult> ValiderCommande()
        {
            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART");
            if (cart == null || !cart.Any())
                return RedirectToAction("Index", "Client");

            var userId = HttpContext.Session.GetInt32("USER_ID");
            if (userId == null)
                return RedirectToAction("Login", "Auth");

            var typeLivraison = HttpContext.Session.GetString("TYPE_LIVRAISON");

            var modePaiementString = HttpContext.Session.GetString("MODE_PAIEMENT");
            if (string.IsNullOrEmpty(modePaiementString))
                return RedirectToAction("Checkout");

            var modePaiement = Enum.Parse<ModePaiement>(modePaiementString);

            decimal total = cart.Sum(i => i.Total);

            var commande = new Commande
            {
                ClientId = userId.Value,
                DateCommande = DateTime.UtcNow,
                MontantTotal = total,
                Statut = StatutCommande.EN_COURS,
                IsPaye = true
            };

            _context.Commandes.Add(commande);
            await _context.SaveChangesAsync();

            foreach (var item in cart)
            {
                _context.CommandeItems.Add(new CommandeItem
                {
                    CommandeId = commande.Id,
                    ProductId = item.ProductId,
                    Quantite = item.Quantite,
                    PrixUnitaire = item.PrixUnitaire
                });
            }

            await _context.SaveChangesAsync();

            if (typeLivraison == "LIVRAISON")
            {
                var livraison = new Livraison
                {
                    CommandeId = commande.Id,
                    Statut = LivraisonStatut.AFFECTEE,
                    DateAffectation = DateTime.UtcNow
                };

                _context.Livraisons.Add(livraison);
                await _context.SaveChangesAsync();
            }

            var paiement = new Paiement
            {
                CommandeId = commande.Id,
                ModePaiement = modePaiement,
                Montant = total,
            };

            _context.Paiements.Add(paiement);
            await _context.SaveChangesAsync();

            HttpContext.Session.Remove("CART");

            return RedirectToAction("MesCommandes");
        }


        [HttpPost]
        public IActionResult Paiement(
            string TYPE_LIVRAISON,
            int? QUARTIER_ID,
            string MODE_PAIEMENT)
        {
            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART");
            if (cart == null || !cart.Any())
                return RedirectToAction("Index", "Client");
            HttpContext.Session.SetString("TYPE_LIVRAISON", TYPE_LIVRAISON);
            HttpContext.Session.SetString("MODE_PAIEMENT", MODE_PAIEMENT);

            if (QUARTIER_ID.HasValue)
                HttpContext.Session.SetInt32("QUARTIER_ID", QUARTIER_ID.Value);
            return RedirectToAction("Paiement");
        }

        [HttpGet]
        public IActionResult Paiement()
        {
            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART");
            if (cart == null || !cart.Any())
                return RedirectToAction("Index", "Client");

            ViewBag.ModePaiement = HttpContext.Session.GetString("MODE_PAIEMENT");
            ViewBag.Total = cart.Sum(i => i.Total);

            return View();
        }



        [HttpPost]
        public async Task<IActionResult> ConfirmerPaiement()
        {
            var cart = HttpContext.Session.GetObjectFromJson<List<CartItem>>("CART");
            if (cart == null || !cart.Any())
                return RedirectToAction("Index", "Client");

            var userId = HttpContext.Session.GetInt32("USER_ID");
            if (userId == null)
                return RedirectToAction("Login", "Auth");

            var typeLivraison = HttpContext.Session.GetString("TYPE_LIVRAISON");
            var modePaiement = Enum.Parse<ModePaiement>(HttpContext.Session.GetString("MODE_PAIEMENT"));

            decimal total = cart.Sum(i => i.Total);
            var commande = new Commande
            {
                ClientId = userId.Value,
                DateCommande = DateTime.UtcNow,
                MontantTotal = total,
                Statut = StatutCommande.EN_COURS,
                IsPaye = true
            };
            _context.Commandes.Add(commande);
            await _context.SaveChangesAsync();
            foreach (var item in cart)
            {
                _context.CommandeItems.Add(new CommandeItem
                {
                    CommandeId = commande.Id,
                    ProductId = item.ProductId,
                    Quantite = item.Quantite,
                    PrixUnitaire = item.PrixUnitaire
                });
            }

            await _context.SaveChangesAsync();
            if (typeLivraison == "LIVRAISON")
            {
                _context.Livraisons.Add(new Livraison
                {
                    CommandeId = commande.Id,
                    Statut = LivraisonStatut.AFFECTEE,
                    DateAffectation = DateTime.UtcNow
                });

                await _context.SaveChangesAsync();
            }
            _context.Paiements.Add(new Paiement
            {
                CommandeId = commande.Id,
                ModePaiement = modePaiement,
                Montant = total,
            });

            await _context.SaveChangesAsync();
            HttpContext.Session.Remove("CART");

            return RedirectToAction("MesCommandes");
        }

        public async Task<IActionResult> MesCommandes()
        {
            var userId = HttpContext.Session.GetInt32("USER_ID");
            if (userId == null)
                return RedirectToAction("Login", "Auth");

            var commandes = await _context.Commandes
                .Where(c => c.ClientId == userId.Value)
                .Include(c => c.CommandeItems)
                    .ThenInclude(ci => ci.Product)
                .OrderByDescending(c => c.DateCommande)
                .ToListAsync();

            return View(commandes);
        }



    }
}