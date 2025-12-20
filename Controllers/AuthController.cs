using gestion_restaurant.Data;
using gestion_restaurant.Models;
using gestion_restaurant.Models.Enums;
using gestion_restaurant.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace gestion_restaurant.Controllers
{
    public class AuthController : Controller
    {
        private readonly ApplicationDbContext _context;

        public AuthController(ApplicationDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult Login()
        {
            return View();
        }

        [HttpGet]
        public IActionResult Register()
        {
            return View();
        }


        [HttpPost]
        public async Task<IActionResult> Login(LoginViewModel model)
        {
            var user = await _context.Users
                .AsNoTracking()
                .FirstOrDefaultAsync(u =>
                    u.Login == model.Login &&
                    u.Password == model.Password &&
                    u.TypeUser == "CLIENT"
                );

            if (user == null)
            {
                ViewBag.Error = "Login ou mot de passe incorrect";
                return View(model);
            }

            HttpContext.Session.SetInt32("USER_ID", (int)user.Id);
            HttpContext.Session.SetString("USER_NAME", user.Prenom);
            if (HttpContext.Session.GetString("commande") != null)
            {
                HttpContext.Session.Remove("commande");
                return RedirectToAction("Paiement", "Commande");
            }
            return RedirectToAction("Index", "Client");
        }

        [HttpPost]
        public async Task<IActionResult> Register(RegisterViewModel model)
        {
            if (!ModelState.IsValid)
                return View(model);
            var exists = await _context.Users
                .AnyAsync(u => u.Login == model.Login);

            if (exists)
            {
                ViewBag.Error = "Ce login existe déjà";
                return View(model);
            }

            var user = new User
            {
                Nom = model.Nom,
                Prenom = model.Prenom,
                Telephone = model.Telephone,
                Login = model.Login,
                Password = model.Password,
                Role = RoleType.CLIENT,
                TypeUser = "CLIENT",
                CreatedAt = DateTime.UtcNow
            };

            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return RedirectToAction("Login");
        }



        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Index", "Client");
        }
    }
}