# 作物

作物基类为 `games.moegirl.sinocraft.sinocore.block.SimpleCropBlock`，该类表示一个一般情况下的单方块作物。该类继承自 `CropBlock`，与 `CropBlock` 相比具有以下特点：

- 可自定义作物生长阶段
- 可配合 `AbstructLootTableProvider` 自动生成对应掉落物的战利品表
- 可配合 `AbstractAutoBlockStateProvider` 自动生成对应方块模型

`SimpleCropBlock` 类不是一个抽象类。一般情况下，该类开箱即用。其两个构造函数如下：

```java
public SimpleCropBlock(Supplier<RegistryObject<T>> crop, int age, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
    // ...
}

public SimpleCropBlock(int age, int minCrop, int maxCrop) {
    // ...
}
```

`age` 参数表示该作物的最大生长阶段。 第一个构造函数包含一个 `crop` 参数表示作物成熟后的产物，之后三个 `count` 参数则是种子和产物的掉落数量，
影响成熟后掉落物数量，该构造函数用于创建类似原版甜菜、马铃薯等带有种子的作物。 第二个构造只需要额外给两个 `count` 参数即可，表示成熟后掉落物数量。
此构造创建的是类似原版胡萝卜等成熟产物与种子为同一个物品的作物。

如果一个作物没有特殊需求，该类中被重写的最常见的方法是 `VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)`。
该方法用于修改不同情况下作物的碰撞体积。

## 双层作物

双层作物，即某个作物由上下两个方块组成，其基类为 `DoubleCropBlock`。该类直接继承自 `DoublePlantBlock`，并实现了 `Crop` 接口。默认情况下，
该类对应的作物 `BlockState` 对象存在对应 age 属性和 `HALF=BlockStateProperties.DOUBLE_BLOCK_HALF` 属性。

在默认双层作物的掉落物中，只破坏上方方块不会掉落任何物品，只有下方方块会掉落固定一个种子和对应成熟时的额外掉落。

与 `SimpleCropBlock` 不同的是，该类为一个抽象类。使用时至少需要实现以下额外的方法：

- `VoxelShape getShape(BlockState, BlockGetter, BlockPos, CollisionContext)`：获取方块的模型大小
- `boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos)`：对应物品可以放置在怎样的方块上。给定的 `pPos`
  和 `pState` 是下方方块的位置，上方方块不会触发该方法的判断，因此该方法往往需要同时判断当前位置和上方位置两个位置是否符合标准
- `boolean canSurviveLower(BlockState state, LevelReader level, BlockPos pos)`：下方方块是否可以放置在对应位置上。
  这里不用判断上方方块是否符合要求。该方法将在 `canSurvive` 方法中调用。

## 实例

创建一个完整的作物方块通常包括以下几步，以 `SinoFoundation` 中的茄子作物为例：

1. 如果作物有产物，先创建和注册对应产物的物品。否则不需要。

    ```java
    // SFDItems 类中
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);
    
    public static final RegistryObject<Item> EGGPLANT = ITEMS.register("eggplant",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
   
    // 在 Mod 主类中调用
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
    ```

2. 创建和注册对应方块，有时也需要新建对应的类。

    ```java
    // SFDBlocks 类中
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SinoFoundation.MODID);
    
    // 茄子的生长具有 7 个阶段，成熟后额外掉落 0-1 个种子和 2-5 个茄子
    public static final RegistryObject<Item> EGGPLANT_PLANT = BLOCKS.register("eggplant",
        () -> new CropBlockWith4Shape<>(SFDItems.EGGPLANT, 7, 0, 1, 2, 5));
   
    // 在 Mod 主类中调用
    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
    ```

   其中，自定义的方块类 `CropBlockWith4Shape` 为一个只有 4 种方块大小的 `SimpleCropBlock` 子类：

    ```java
    public class CropBlockWith4Shape<T extends Item> extends SimpleCropBlock<T> {
    
        // 四种方块大小
        protected static final VoxelShape[] SHAPES = new VoxelShape[]{
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(0, 0, 0, 16, 5, 16),
                Block.box(0, 0, 0, 16, 7, 16),
                Block.box(0, 0, 0, 16, 9, 16),
        };
    
        public CropBlockWith4Shape(Supplier<RegistryObject<T>> crop, int age, int minSeedCount, int maxSeedCount, int minCropCount, int maxCropCount) {
            super(crop, age, minSeedCount, maxSeedCount, minCropCount, maxCropCount);
        }
    
        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            int age = state.getValue(getAgeProperty());
            // 选择不同的放块大小
            int stage = age / ((getMaxAge() + 1) / 4);
            return SHAPES[stage];
        }
    }
    ```

3. 创建和注册方块对应的物品。通常来说方块对应的物品就是该作物的种子。

    ```java
    // SFDBlockItems 类中
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SinoFoundation.MODID);
    
    public static final RegistryObject<BlockItem> EGGPLANT_SEED = ITEMS.register("eggplant_seed",
        () -> new ItemNameBlockItem(SFDBlocks.EGGPLANT_PLANT.get(), new Item.Properties()));
   
    // 在 Mod 主类中调用
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
    ```

4. 创建并注册一个继承自 `AbstructLootTableProvider` 类的 `DataProvider`，在其中注册该方块。通常来说，直接注册对应的 `DeferredRegister`
   相当于注册该 `DeferredRegister` 中的所有方块。该 `DataProvider` 用于生成作物的掉落物表。

    ```java
    @Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class SFDData {
        @SubscribeEvent
        public static void onGatherData(GatherDataEvent event) {
            DataGenerator gen = event.getGenerator();
            PackOutput output = gen.getPackOutput();
            gen.addProvider(true, new SFDBlockLootTableProvider(output, SinoFoundation.MODID));
        }
    }
    ```

   在 `SFDBlockLootTableProvider` 中，我们只需要添加对应的 `Block` 即可。具体掉落内容我们已经在 `SFDBlocks.EGGPLANT_PLANT` 方块中配置了。

    ```java
    public class SFDBlockLootTableProvider extends AbstractAutoBlockStateProvider {
    
        public SFDBlockLootTableProvider(PackOutput output, String modid) {
            super(output, modid);
        }
    
        @Override
        public void getTables(List<SubProviderEntry> tables) {
            // 导入 DeferredRegister 中的所有方块
            addBlocks(SFDBlocks.BLOCKS);
        }
    }
    ```

5. 创建并注册继承自 `AbstractAutoBlockStateProvider` 类的 `DataProvider`，并在其中注册作物方块。

    ```java
    @Mod.EventBusSubscriber(modid = SinoFoundation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class SFDData {
        @SubscribeEvent
        public static void onGatherData(GatherDataEvent event) {
            DataGenerator gen = event.getGenerator();
            ExistingFileHelper exHelper = event.getExistingFileHelper();
            PackOutput output = gen.getPackOutput();
    
            gen.addProvider(true, new SFDBlockLootTableProvider(output, SinoFoundation.MODID));
            gen.addProvider(true, new SFDBlockStateProvider(output, SinoFoundation.MODID, exHelper, SFDBlocks.BLOCKS));
        }
    }
    ```

   `AbstractAutoBlockStateProvider` 类内置一个 `crop()` 方法可快捷添加作物，适用于任何实现 `Crop` 接口的 `Block` 类对象。

    ```java
    public class SFDBlockStateProvider extends AbstractAutoBlockStateProvider {

        @SafeVarargs
        public SFDBlockStateProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper, DeferredRegister<? extends Block>... deferredRegisters) {
             super(output, modId, existingFileHelper, deferredRegisters);
        }
    
        @Override
        protected void registerBlockStatesAndModels() {
            crop(SFDBlocks.EGGPLANT_PLANT);
        }
    }
    ```

   此时如果运行一次 `runData`，可以看见缺失的所有材质。生成器会自动判断是否存在 `BlockStateProperties.DOUBLE_BLOCK_HALF` 属性，若存在则
   认为是双层作物，其材质命名规则为：

    - 上层作物方块: `作物方块名_stage_top_作物age属性`
    - 下层作物方块: `作物方块名_stage_bottom_作物age属性`

   单层作物材质命名规则为：`作物方块名_stage_作物age属性`，如茄子的材质包括以下几个：

    - `resources/assets/sinofoundation/textures/block/eggplant_stage_0.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_1.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_2.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_3.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_4.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_5.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_6.png`
    - `resources/assets/sinofoundation/textures/block/eggplant_stage_7.png`

6. 实现并注册 `ItemModelProvider`，`LanguageProvider` 等其他配件，并运行 `runData` 任务，根据输出准备好对应材质
