using System;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    public class Complement
    {
        public long Id { get; set; }
        public string Nom { get; set; }
        public decimal Prix { get; set; }
        public string Image { get; set; }
        public ComplementType TypeComplement { get; set; }
        public string ImagePublicId { get; set; }
        public string Description { get; set; }
        public DateTime CreatedAt { get; set; }
    }
}
