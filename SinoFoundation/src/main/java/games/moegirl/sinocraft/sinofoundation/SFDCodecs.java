package games.moegirl.sinocraft.sinofoundation;

import com.mojang.serialization.Codec;
import games.moegirl.sinocraft.sinofoundation.data.gen.loot_modifier.SeedDropModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDCodecs {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_CODECS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SinoFoundation.MODID);

    public static final RegistryObject<Codec<SeedDropModifier>> SEED_DROP_MODIFIER =
            LOOT_MODIFIER_CODECS.register("seed_drop", () -> SeedDropModifier.SEED_DROP_MODIFIER_CODEC);

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_CODECS.register(bus);
    }
}
