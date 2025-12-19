using NpgsqlTypes;

namespace gestion_restaurant.Models.Enums
{
    public enum StatutCommande
    {
        [PgName("ENCOURS")]
        EN_COURS,
        [PgName("TERMINE")]
        TERMINE,
        [PgName("LIVRE")]
        LIVRE
    }
}
