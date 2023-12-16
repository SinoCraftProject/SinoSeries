# ILootableBlock

`games.moegirl.sinocraft.sinocore.block.ILootableBlock` 接口用于在 `Block` 类中定义掉落物。该接口有且仅有一个方法 `createLootBuilder` 用于生成方块掉落物的战利品表。

```java
/**
 * 在自定义的 Block 类中实现，用于 DataProvider 自动注册其掉落物
 */
public interface ILootableBlock {

    LootTable.Builder createLootBuilder(BlockLootables helper);
}
```

`BlockLootables` 工具可以辅助创建各种常见用于掉落物的战利品表。其内容与 `BlockLootSubProvider` 类提供的工具方法一致。

在配合 `AbstructLootTableProvider` 使用时，可自动根据该方法生成方块战利品。详见数据生成部分相关内容。