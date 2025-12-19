using NpgsqlTypes;

namespace gestion_restaurant.Models.Enums
{
    public enum ModePaiement
    {
        [PgName("WAVE")]
        WAVE,
        [PgName("OM")]
        OM
    }
}
