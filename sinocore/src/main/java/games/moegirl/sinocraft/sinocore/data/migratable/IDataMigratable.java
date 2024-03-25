package games.moegirl.sinocraft.sinocore.data.migratable;

/**
 * Interface for migrate-able data class.
 * @param <UPPER> Upper version data class.
 * @param <LOWER> Lower version data class.
 */
public interface IDataMigratable<UPPER, LOWER> {
    UPPER up();

    LOWER down();
}
