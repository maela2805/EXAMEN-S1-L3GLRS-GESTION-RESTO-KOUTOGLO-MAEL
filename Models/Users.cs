using System.ComponentModel.DataAnnotations.Schema;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    [Table("users")]
    public class User
    {
        [Column("id")]
        public long Id { get; set; }

        [Column("nom")]
        public string Nom { get; set; }

        [Column("prenom")]
        public string Prenom { get; set; }

        [Column("telephone")]
        public string Telephone { get; set; }

        [Column("login")]
        public string Login { get; set; }

        [Column("password")]
        public string Password { get; set; }

        // [Column("role")]
        // public string Role { get; set; }
        [Column("role")]
        public RoleType Role { get; set; }
        [Column("type_user",TypeName ="role_type")]
        public string TypeUser { get; set; }

        [Column("created_at")]
        public DateTime CreatedAt { get; set; }
    }
}