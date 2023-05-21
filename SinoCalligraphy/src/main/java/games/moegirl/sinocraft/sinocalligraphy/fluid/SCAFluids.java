package games.moegirl.sinocraft.sinocalligraphy.fluid;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.block.SCABlocks;
import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SCAFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, SinoCalligraphy.MODID);

    public static void register(IEventBus bus) {
        FLUIDS.register(bus);
    }

    public static final RegistryObject<FlowingFluid> WOOD_PULP = FLUIDS.register("wood_pulp", () -> new ForgeFlowingFluid.Source(SCAFluids.WOOD_PULP_PROPERTY));
    public static final RegistryObject<FlowingFluid> WOOD_PULP_FLOWING = FLUIDS.register("wood_pulp_flowing", () -> new ForgeFlowingFluid.Flowing(SCAFluids.WOOD_PULP_PROPERTY));

    public static final ForgeFlowingFluid.Properties WOOD_PULP_PROPERTY = new ForgeFlowingFluid.Properties(SCAFluidTypes.WOOD_PULP_TYPE, WOOD_PULP, WOOD_PULP_FLOWING)
            .levelDecreasePerBlock(2)
            .slopeFindDistance(2)
            .block(SCABlocks.WOOD_PULP_BLOCK)
            .bucket(SCAItems.WOOD_PULP_BUCKET);

}
