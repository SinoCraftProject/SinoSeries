package games.moegirl.sinocraft.sinofoundation.block;

import games.moegirl.sinocraft.sinocore.block.SimpleCropBlock;
import games.moegirl.sinocraft.sinocore.utility.Functions;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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

    public static final RegistryObject<CropBlockWith4Shape<Item>> WHITE_RADISH_PLANT = crop3("white_radish_plant", 0, 4);
    public static final RegistryObject<CropBlockWith4Shape<Item>> SUMMER_RADISH_PLANT = crop3("summer_radish_plant", 0, 4);
    public static final RegistryObject<CropBlockWith4Shape<Item>> GREEN_RADISH_PLANT = crop3("green_radish_plant", 0, 4);
    public static final RegistryObject<CropBlockWith4Shape<Item>> CHILI_PEPPER_PLANT = pepper(() -> SFDItems.CHILI_PEPPER, "chili_pepper_plant");
    public static final RegistryObject<CropBlockWith4Shape<Item>> GREEN_PEPPER_PLANT = pepper(() -> SFDItems.CHILI_PEPPER, "green_pepper_plant");
    public static final RegistryObject<CropBlockWith4Shape<Item>> CABBAGE_PLANT = block("celery_cabbage", () -> new CropBlockWith4Shape<>(() -> SFDItems.CABBAGE, 3, 0, 1, 2, 5));
    public static final RegistryObject<CropBlockWith4Shape<Item>> EGGPLANT_PLANT = crop7(() -> SFDItems.EGGPLANT, "eggplant", 5);
    public static final RegistryObject<CropBlockWith4Shape<Item>> MILLET_PLANT = crop7(() -> SFDItems.MILLET, "millet", 6);
    public static final RegistryObject<CropBlockWith4Shape<Item>> SOYBEAN_PLANT = crop7("soybean", 1, 4);
    public static final RegistryObject<CropBlockWith4Shape<Item>> GARLIC_PLANT = crop7("garlic", 2, 4);

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

    public static final RegistryObject<Lantern> LANTERN = block("lantern", () -> new Lantern(3.5f, MapColor.METAL));
    public static final RegistryObject<Lantern> STONE_LANTERN = block("stone_lantern", () -> new Lantern(5.0f, MapColor.STONE));

    private static <T extends Block> RegistryObject<T> block(Class<T> blockClass) {
        return BLOCKS.register(NameUtils.to_snake_name(blockClass.getSimpleName()), Functions.constructor(blockClass));
    }

    public static <T extends Block> RegistryObject<T> block(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static <T extends Item> RegistryObject<SimpleCropBlock<T>> crop3(Supplier<RegistryObject<T>> crop, String name, int minCropCount, int maxCropCount) {
        return block(name, () -> new SimpleCropBlock<>(crop, 3, 0, 1, minCropCount, maxCropCount));
    }

    private static RegistryObject<CropBlockWith4Shape<Item>> pepper(Supplier<RegistryObject<Item>> crop, String name) {
        return block(name, () -> new CropBlockWith4Shape<>(crop, 7, 0, 1, 2, 4));
    }

    private static RegistryObject<CropBlockWith4Shape<Item>> crop7(Supplier<RegistryObject<Item>> crop, String name, int maxDrop) {
        return block(name, () -> new CropBlockWith4Shape<>(crop, 7, 0, 1, 2, maxDrop));
    }

    private static RegistryObject<CropBlockWith4Shape<Item>> crop7(String name, int minDrop, int maxDrop) {
        return block(name, () -> new CropBlockWith4Shape<>(7, minDrop, maxDrop));
    }

    private static RegistryObject<CropBlockWith4Shape<Item>> crop3(String name, int minCrop, int maxCrop) {
        return block(name, () -> new CropBlockWith4Shape<>(3, minCrop, maxCrop));
    }
}
