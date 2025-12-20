using NpgsqlTypes;

namespace gestion_restaurant.Models.Enums
{
    public enum RoleType
    {
        [PgName("CLIENT")]
        CLIENT,
        [PgName("GESTIONNAIRE")]
        GESTIONNAIRE,
        [PgName("LIVREUR")]
        LIVREUR
    }
}
