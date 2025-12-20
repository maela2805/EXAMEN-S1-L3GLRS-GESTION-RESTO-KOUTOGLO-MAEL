using gestion_restaurant.Data;
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

            return RedirectToAction("Accueil", "Client");
        }


        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
        }
    }
}