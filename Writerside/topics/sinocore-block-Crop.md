# Crop


`games.moegirl.sinocraft.sinocore.block.Crop<T>` 接口表示一个方块为作物，其泛型类型 T 表示成熟后产物物品类型。该接口由以下几分组成：

- `BonemealableBlock`：该接口表示对应方块可以对骨粉产生响应。接口对其实现为：作物生长周期为 3 时随即增加 0-2，否则增加 2-5。
- `ILootableBlock` 接口与相关方法：该接口定义了作物的默认掉落物。新增了一个 `createLootBuilder` 方法的默认实现，用于根据作物生成战利品表。该方法的默认行为包括：

    1. 无论作物是否成熟，固定掉落一个种子
    2. 若作物成熟，额外随机掉落 \[minSeedCount, maxSeedCount\] 颗种子和 \[minCropCount, maxCropCount\] 个作物。

```java
default LootTable.Builder createLootBuilder(BlockLootables helper,
                                            int minSeedCount, int maxSeedCount,
                                            int minCropCount, int maxCropCount) {
    // ...
}
```

- 作物本身的一些属性和成长行为。这些方法多与 `CropBlock` 的相关方法重合。主要用于规范实际实现了作物的行为，但不继承自 `CropBlock` 类的作物，如双层作物等。相关方法主要包括：

    - `getBaseSeedId`, `getCrop`：获取作物对应种子和成熟后产物的对应物品。默认 `getBaseSeedId` 返回的是当前方块对应的物品。
    - `getAgeProperty`, `getMaxAge`：获取标记作物成长的 `Property` 和作物最大成长阶段。
    - `getAge`, `isMaxAge`, `getStateForAge`：`BlockState` 与作物成长阶段的互操作，用于获取和设置对应作物的阶段及检验是否成熟。
    - `growCrops`：实现作物成长。
    - `getGrowthSpeed`：主要用于在 `randomTick` 中调用，计算自然条件下作物的生长因子，默认使用与 `CropBlock` 相同的实现，其返回值应在 \[0, 6\] 范围内。参考 Minecraft 和 Forge 的默认实现，一个单方块作物 `randomTick` 方法实现方式为：

```java
@Override
public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (!level.isAreaLoaded(pos, 1))
        return; // Forge: prevent loading unloaded chunks when checking neighbor's light
    if (!isMaxAge(state) && level.getRawBrightness(pos, 0) >= 9) {
        float f = getGrowthSpeed(level, pos);
        if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
            growCrops(level, pos, state, false);
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }
}
```

- 用于获取生长阶段的 `Property` 对象的静态工具方法 `getAgeProperties`。通过该方法可以获取官方定义的 8 种成长阶段属性，也可以唯一的创建和获取任意长度的属性。
