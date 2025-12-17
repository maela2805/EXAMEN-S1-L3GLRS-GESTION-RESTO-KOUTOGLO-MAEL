using System;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    public class Commande
    {
        public long Id { get; set; }
        public long? ClientId { get; set; }
        public User Client { get; set; }
        public DateTime DateCommande { get; set; }
        public decimal MontantTotal { get; set; }
        public StatutCommande Statut { get; set; }
        public bool IsPaye { get; set; }
        public long? LivraisonId { get; set; }
        public Livraison Livraison { get; set; }
    }
}
