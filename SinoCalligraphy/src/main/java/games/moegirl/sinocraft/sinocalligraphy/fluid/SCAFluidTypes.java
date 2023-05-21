package games.moegirl.sinocraft.sinocalligraphy.fluid;

import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocore.fluid.FluidTypeBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class SCAFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, SinoCalligraphy.MODID);

    public static void register(IEventBus bus) {
        FLUID_TYPES.register(bus);
    }

    public static final ResourceLocation WATER_STILL_LOCATION = new ResourceLocation(SinoCalligraphy.MODID, "fluid/water_still");
    public static final ResourceLocation WATER_FLOWING_LOCATION = new ResourceLocation(SinoCalligraphy.MODID, "fluid/water_flow");
    public static final ResourceLocation WATER_OVERLAY_LOCATION = new ResourceLocation(SinoCalligraphy.MODID, "fluid/water_overlay");

    public static final RegistryObject<FluidType> WOOD_PULP_TYPE = FLUID_TYPES.register("wood_pulp", () -> new FluidTypeBase(
            FluidType.Properties.create()
                    .descriptionId(SCAConstants.DESCRIPTION_ID_WOOD_PULP)
                    .density(3000)
                    .viscosity(6000)
                    .lightLevel(0)
                    .temperature(25)
                    .canSwim(false)
                    .canConvertToSource(false)
                    .supportsBoating(true)
                    .canExtinguish(true)
                    .canHydrate(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH),
            WATER_STILL_LOCATION, WATER_FLOWING_LOCATION, WATER_OVERLAY_LOCATION,
            0xFFBC8129, new Vector3f(0xBC / 255f, 0x81 / 255f, 0x29 / 255f), 3
    ));
}
