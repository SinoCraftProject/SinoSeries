package games.moegirl.sinocraft.sinodeco.entity;

import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;

public class SDEntities {
    public static final IRegistry<EntityType<?>> ENTITY_TYPES = RegistryManager.create(SinoDeco.MODID, Registries.ENTITY_TYPE);

    public static void register() {
        // Todo: register
        ENTITY_TYPES.register();
    }

    public static final IRegRef<EntityType<DummyChairEntity>> DUMMY_CHAIR = ENTITY_TYPES.register("dummy_chair", () -> EntityType.Builder.of(DummyChairEntity::new, MobCategory.MISC)
            .sized(0.6F, 0.6F)
            .passengerAttachments(new Vec3(0, 0, 0))
            .fireImmune()
            .noSummon()
            .build("dummy_chair"));
}
