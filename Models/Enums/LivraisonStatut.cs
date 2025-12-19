using NpgsqlTypes;

namespace gestion_restaurant.Models.Enums
{
    public enum LivraisonStatut
    {
        [PgName("AFFECTEE")]
        AFFECTEE,
        [PgName("EN_COURS")]
        EN_COURS,
        [PgName("TERMINE")]
        TERMINE,
        [PgName("ANNULEE")]
        ANNULEE
    }
}
