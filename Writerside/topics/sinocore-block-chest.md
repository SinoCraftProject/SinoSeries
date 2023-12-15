# 箱子

箱子类是实现树时的一种副产物。

- `ChestBlockBase` 类对应箱子的基类，直接创建则是普通箱子，对应 `BlockEntity` 为 `SimpleChestBlockEntity`
- `TrappedChestBlockBase` 实现了陷阱箱的行为，对应 `BlockEntity` 为 `SimpleTrappedChestBlockEntity`
- `ChestBlockBase#verifyTexture` 可用于校验箱子纹理是否存在。如果不存在则会在 log 中输出缺失的纹理。

使用该类创建的箱子支持与本 Mod 的各种 DataProvider 相配合：

- 使用 `AbstractAutoBlockStateProvider#chest` 创建模型
- 使用 `AbstractAutoItemModelProvider#chest` 创建物品模型
- 使用 `AbstractBlockTagsProvider#chest` 为箱子方块添加默认 Tag
- 使用 `AbstractItemTagsProvider#chest` 为箱子物品添加默认 Tag
- 使用 `AbstractRecipeProvider#chest` 为箱子添加默认配方
- 使用 `AbstructLootTableProvider#addBlock` 为箱子添加掉落物
