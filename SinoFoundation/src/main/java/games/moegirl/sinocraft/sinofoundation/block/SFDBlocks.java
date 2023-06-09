package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantBlock;
import games.moegirl.sinocraft.sinofoundation.block.plant.PlantType;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SFDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final RegistryObject<Block> STOVE = BLOCKS.register("stove", StoveBlock::new);
    public static final RegistryObject<Block> WOOD_DESK = BLOCKS.register("wood_desk", WoodDeskBlock::new);

    public static final RegistryObject<PlantBlock> WHITE_RADISH_PLANT = BLOCKS.register("white_radish_plant", () -> new PlantBlock(PlantType.WHITE_RADISH, SFDBlockItems.WHITE_RADISH));
    public static final RegistryObject<PlantBlock> SUMMER_RADISH_PLANT = BLOCKS.register("summer_radish_plant", () -> new PlantBlock(PlantType.SUMMER_RADISH, SFDBlockItems.SUMMER_RADISH));
    public static final RegistryObject<PlantBlock> GREEN_RADISH_PLANT = BLOCKS.register("green_radish_plant", () -> new PlantBlock(PlantType.GREEN_RADISH, SFDBlockItems.GREEN_RADISH));
    public static final RegistryObject<PlantBlock> CHILI_PEPPER_PLANT = BLOCKS.register("chili_pepper_plant", () -> new PlantBlock(PlantType.CHILI_PEPPER, SFDBlockItems.CHILI_PEPPER_SEED));
    public static final RegistryObject<PlantBlock> GREEN_PEPPER_PLANT = BLOCKS.register("green_pepper_plant", () -> new PlantBlock(PlantType.GREEN_PEPPER, SFDBlockItems.GREEN_PEPPER_SEED));
    public static final RegistryObject<PlantBlock> EGGPLANT_PLANT = BLOCKS.register("eggplant_plant", () -> new PlantBlock(PlantType.EGGPLANT, SFDBlockItems.EGGPLANT_SEED));
    public static final RegistryObject<PlantBlock> CABBAGE_PLANT = BLOCKS.register("cabbage_plant", () -> new PlantBlock(PlantType.CABBAGE, SFDBlockItems.CABBAGE_SEED));
    public static final RegistryObject<PlantBlock> MILLET_PLANT = BLOCKS.register("millet_plant", () -> new PlantBlock(PlantType.MILLET, SFDBlockItems.MILLET_SEED));
    public static final RegistryObject<PlantBlock> SOYBEAN_PLANT = BLOCKS.register("soybean_plant", () -> new PlantBlock(PlantType.SOYBEAN, SFDBlockItems.SOYBEAN));
    public static final RegistryObject<PlantBlock> GARLIC_PLANT = BLOCKS.register("garlic_plant", () -> new PlantBlock(PlantType.GARLIC, SFDBlockItems.GARLIC));

    public static final RegistryObject<Block> MARBLE = BLOCKS.register("marble", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));
    public static final RegistryObject<WallBlock> MARBLE_WALL = BLOCKS.register("marble_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MARBLE.get())));

    public static final RegistryObject<DropExperienceBlock> JADE_ORE = BLOCKS.register("jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().explosionResistance(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> NITER_ORE = BLOCKS.register("niter_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().explosionResistance(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> SULPHUR_ORE = BLOCKS.register("sulphur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().explosionResistance(3.0f), UniformInt.of(2, 5)));
}
