package games.moegirl.sinocraft.sinodeco.entity;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SDEntities {
    public static final IRegistry<EntityType<?>> ENTITY_TYPES = RegistryManager.obtain(SinoDeco.MODID, Registries.ENTITY_TYPE);

    public static void register() {
        // Todo: register
        ENTITY_TYPES.register();
    }

    public static final IRegRef<EntityType<DummyChairEntity>> DUMMY_CHAIR = ENTITY_TYPES.register("dummy_chair", () -> EntityType.Builder.of(DummyChairEntity::new, MobCategory.MISC)
            .sized(1, 1)
            .fireImmune()
            .noSave()
            .noSummon()
            .build("dummy_chair"));
}
