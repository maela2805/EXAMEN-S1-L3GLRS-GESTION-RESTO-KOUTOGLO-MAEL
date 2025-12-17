using System;
using gestion_restaurant.Models.Enums;

namespace gestion_restaurant.Models
{
    public class Paiement
    {
        public long Id { get; set; }
        public long? CommandeId { get; set; }
        public Commande Commande { get; set; }
        public decimal Montant { get; set; }
        public DateTime DatePaiement { get; set; }
        public ModePaiement ModePaiement { get; set; }
    }
}
