package games.moegirl.sinocraft.sinocore.registry;

public interface IRegistrable<T> {

    /**
     * 当前 Mod Id
     *
     * @return Mod id
     */
    String modId();

    /**
     * 进行注册
     * <ul>
     *     <li>Forge / NeoForge：在 Mod 主类里调用</li>
     *     <li>Fabric：在 {@code ModInitializer#onInitialize} 中调用</li>
     * </ul>
     */
    void register();
}
