using System.ComponentModel.DataAnnotations;

namespace gestion_restaurant.ViewModels
{
    public class RegisterViewModel
    {
        [Required]
        public string Nom { get; set; }

        [Required]
        public string Prenom { get; set; }

        [Required]
        public string Telephone { get; set; }

        [Required]
        public string Login { get; set; }

        [Required]
        [DataType(DataType.Password)]
        public string Password { get; set; }
    }
}
