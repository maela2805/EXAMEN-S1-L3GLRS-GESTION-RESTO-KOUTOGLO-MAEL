using System;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    public class Livraison
    {
        public long Id { get; set; }
        public long? CommandeId { get; set; }
        public long? LivreurId { get; set; }
        public DateTime DateAffectation { get; set; }
        public DateTime? DateLivraison { get; set; }
        public LivraisonStatut Statut { get; set; }
    }
}
