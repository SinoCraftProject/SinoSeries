package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.block.SimpleCropBlock;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.plant.*;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SFDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoFoundation.MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final RegistryObject<Block> STOVE = BLOCKS.register("stove", StoveBlock::new);
    public static final RegistryObject<Block> WOOD_DESK = BLOCKS.register("wood_desk", WoodDeskBlock::new);
    public static final RegistryObject<Block> WOOD_CHAIRS = BLOCKS.register("wood_chairs", WoodChairsBlock::new);
    public static final RegistryObject<Block> BAMBOO_PLAQUE = BLOCKS.register("bamboo_plaque", BambooPlaqueBlock::new);

    public static final RegistryObject<Block> PURPLE_CLAY = BLOCKS.register("purple_clay", () -> new Block(BlockBehaviour.Properties.of()));

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

    public static final RegistryObject<Block> MARBLE_BLOCK = BLOCKS.register("marble_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).requiresCorrectToolForDrops().strength(1.5f, 6.0f)));
    public static final RegistryObject<Block> SMOOTH_MARBLE = BLOCKS.register("smooth_marble", () -> new Block(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<RotatedPillarBlock> MARBLE_PILLAR = BLOCKS.register("marble_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<RotatedPillarBlock> CHISELED_MARBLE_BLOCK = BLOCKS.register("chiseled_marble_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<SlabBlock> MARBLE_SLAB = BLOCKS.register("marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<StairBlock> MARBLE_STAIRS = BLOCKS.register("marble_stairs", () -> new StairBlock(() -> MARBLE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<WallBlock> MARBLE_WALLS = BLOCKS.register("marble_walls", () -> new WallBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<SlabBlock> SMOOTH_MARBLE_SLAB = BLOCKS.register("smooth_marble_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));
    public static final RegistryObject<StairBlock> SMOOTH_MARBLE_STAIRS = BLOCKS.register("smooth_marble_stairs", () -> new StairBlock(() -> MARBLE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(MARBLE_BLOCK.get())));

    public static final RegistryObject<DropExperienceBlock> BLACK_JADE_ORE = BLOCKS.register("black_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_BLACK_JADE_ORE = BLOCKS.register("deepslate_black_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> GREEN_JADE_ORE = BLOCKS.register("green_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_GREEN_JADE_ORE = BLOCKS.register("deepslate_green_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> RED_JADE_ORE = BLOCKS.register("red_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_RED_JADE_ORE = BLOCKS.register("deepslate_red_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> WHITE_JADE_ORE = BLOCKS.register("white_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_WHITE_JADE_ORE = BLOCKS.register("deepslate_white_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> YELLOW_JADE_ORE = BLOCKS.register("yellow_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_YELLOW_JADE_ORE = BLOCKS.register("deepslate_yellow_jade_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> NITER_ORE = BLOCKS.register("niter_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> SULPHUR_ORE = BLOCKS.register("sulphur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> NETHER_SULPHUR_ORE = BLOCKS.register("nether_sulphur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK).requiresCorrectToolForDrops().strength(3.0f), UniformInt.of(2, 5)));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_SULPHUR_ORE = BLOCKS.register("deepslate_sulphur_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(4.5f, 3.0f), UniformInt.of(2, 5)));

    public static final RegistryObject<JujubeChest> JUJUBE_CHEST = block(JujubeChest.class);
    public static final RegistryObject<JujubeTrappedChest> JUJUBE_TRAPPED_CHEST = block(JujubeTrappedChest.class);

    public static final RegistryObject<SimpleCropBlock<Item>> WORMWOOD = crop3(() -> SFDItems.WORMWOOD_LEAF, "wormwood", 2, 2);
    public static final RegistryObject<Rice> RICE = block(Rice.class);
    public static final RegistryObject<SimpleCropBlock<Item>> SESAME = crop3(() -> SFDItems.SESAME, "sesame", 3, 3);

    private static <T extends Block> RegistryObject<T> block(Class<T> blockClass) {
        return BLOCKS.register(NameUtils.to_snake_name(blockClass.getSimpleName()), Functions.constructor(blockClass));
    }

    public static <T extends Block> RegistryObject<T> block(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static <T extends Item> RegistryObject<SimpleCropBlock<T>> crop3(Supplier<RegistryObject<T>> crop, String name, int minCropCount, int maxCropCount) {
        return block(name, () -> SimpleCropBlock.create(crop, 3, 0, 1, minCropCount, maxCropCount));
    }
}
