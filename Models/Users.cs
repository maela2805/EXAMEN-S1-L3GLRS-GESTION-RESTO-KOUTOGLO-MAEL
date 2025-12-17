using System;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    public class User
    {
        public long Id { get; set; }
        public string Nom { get; set; }
        public string Prenom { get; set; }
        public string Telephone { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public RoleType Role { get; set; }
        public string TypeUser { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}
