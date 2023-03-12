package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.mixin_inter.IIntrinsicHolderTagsProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.function.Consumer;

@SuppressWarnings("JavadocReference")
public record TreeRegister(Tree tree) {

    /**
     * 用于事件注册
     */
    public void registerEvents(IEventBus bus) {
        new TreeClientModEventRegister(tree, bus);
    }

    /**
     * 注册中文名 DataProvider
     * <p>应当在 {@link LanguageProvider#addTranslations()} 或与之等价的方法中调用</p>
     * @param name 树名
     */
    public void addLanguagesZh(LanguageProvider provider, String name) {
        String woodName = name.endsWith("树") ? name.substring(0, name.length() - 1) : name;
        provider.addBlock(tree.sapling, name + "树苗");
        provider.addBlock(tree.log, name + "原木");
        provider.addBlock(tree.strippedLog, "去皮" + name + "原木");
        provider.addBlock(tree.wood, woodName + "木");
        provider.addBlock(tree.strippedWood, "去皮" + woodName + "木");
        provider.addBlock(tree.leaves, name + "树叶");
        provider.addBlock(tree.pottedSapling, name + "树苗盆栽");
    }

    /**
     * 注册英文名 DataProvider
     * <p>应当在 {@link LanguageProvider#addTranslations()} 或与之等价的方法中调用</p>
     * <p>树名根据注册名推断。取 Tree 注册名以空格替换 _ 获得</p>
     */
    public void addLanguagesEn(LanguageProvider provider) {
        StringBuilder sb = new StringBuilder();
        for (String s : tree.name().getPath().split("_")) {
            if (s.isEmpty()) continue;
            String s1 = s.toLowerCase(Locale.ROOT);
            sb.append(Character.toUpperCase(s1.charAt(0)));
            sb.append(s1.substring(1));
        }
        addLanguagesEn(provider, sb.toString());
    }

    /**
     * 注册英文名 DataProvider
     * <p>应当在 {@link LanguageProvider#addTranslations()} 或与之等价的方法中调用</p>
     * @param name 树名
     */
    public void addLanguagesEn(LanguageProvider provider, String name) {
        provider.addBlock(tree.sapling, name + " Sapling");
        provider.addBlock(tree.log, name + " Log");
        provider.addBlock(tree.strippedLog, "Stripped" + name + " Stripped");
        provider.addBlock(tree.wood, name + " Wood");
        provider.addBlock(tree.strippedWood, "Stripped " + name + " Wood");
        provider.addBlock(tree.leaves, name + " Leaves");
        provider.addBlock(tree.pottedSapling, "Potted " + name + " Sapling");
    }

    /**
     * 注册方块模型 DataProvider
     * <p>应当在 {@link BlockStateProvider#registerStatesAndModels} 或与之等价的方法中调用</p>
     */
    public void addBlockModels(BlockStateProvider provider) {
        provider.simpleBlock(tree.sapling(),
                provider.models().cross(tree.sapling.getId().getPath(), provider.blockTexture(tree.sapling())));
        provider.logBlock(tree.log());
        provider.logBlock(tree.strippedLog());
        provider.simpleBlock(tree.wood(), provider.models().cubeColumn(tree.wood.getId().getPath(),
                provider.blockTexture(tree.log()),
                provider.blockTexture(tree.log())));
        provider.simpleBlock(tree.strippedWood(), provider.models().cubeColumn(tree.strippedWood.getId().getPath(),
                provider.blockTexture(tree.strippedLog()),
                provider.blockTexture(tree.strippedLog())));
        provider.simpleBlock(tree.leaves(), provider.models().singleTexture(
                tree.leaves.getId().getPath(),
                provider.mcLoc(ModelProvider.BLOCK_FOLDER + "/leaves"),
                "all", provider.blockTexture(tree.leaves())));
        provider.simpleBlock(tree.pottedSapling(), provider.models().singleTexture(
                tree.pottedSapling.getId().getPath(),
                provider.mcLoc(ModelProvider.BLOCK_FOLDER + "/flower_pot_cross"),
                "plant", provider.modLoc(ModelProvider.BLOCK_FOLDER + "/" + tree.sapling.getId().getPath())));
    }

    /**
     * 注册物品模型 DataProvider
     * <p>应当在 {@link ItemModelProvider#registerModels} 或与之等价的方法中调用</p>
     */
    public void addItemModels(ItemModelProvider provider) {
        addItem(tree.sapling, provider);
        addBlockItem(tree.log, provider);
        addBlockItem(tree.strippedLog, provider);
        addBlockItem(tree.wood, provider);
        addBlockItem(tree.strippedWood, provider);
        addBlockItem(tree.leaves, provider);
    }

    private void addItem(RegistryObject<? extends Block> sapling, ItemModelProvider provider) {
        String path = sapling.getId().getPath();
        provider.withExistingParent(path, provider.mcLoc("item/handheld"))
                .texture("layer0", provider.modLoc("block/" + path));
    }

    private void addBlockItem(RegistryObject<? extends Block> block, ItemModelProvider provider) {
        String path = block.getId().getPath();
        provider.withExistingParent(path, provider.modLoc("block/" + path));
    }

    /**
     * 注册配方 DataProvider
     * <p>应当在 {@link RecipeProvider#buildRecipes(Consumer)} 或与之等效的方法中调用</p>
     */
    public void addRecipes(Consumer<FinishedRecipe> writer) {
        InventoryChangeTrigger.TriggerInstance hasLog = InventoryChangeTrigger.TriggerInstance
                .hasItems(ItemPredicate.Builder.item().of(tree.log()).build());
        // woodFromLogs
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tree.wood(), 3).group("bark")
                .define('#', tree.log())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_log", hasLog)
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, tree.strippedWood(), 3).group("bark")
                .define('#', tree.strippedLog())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_log", hasLog)
                .save(writer);
    }

    /**
     * 注册配方 DataProvider
     * <p>应当在 {@link RecipeProvider#buildRecipes(Consumer)} 或与之等效的方法中调用</p>
     * <p>该版本会额外添加 1*log -> 4*planks 配方</p>
     */
    public void addRecipes(Consumer<FinishedRecipe> writer, Block planks) {
        // planksFromLog
        InventoryChangeTrigger.TriggerInstance hasLogs = InventoryChangeTrigger.TriggerInstance
                .hasItems(ItemPredicate.Builder.item().of(ItemTags.LOGS).build());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, planks, 4).group("planks")
                .requires(tree.log())
                .unlockedBy("has_log", hasLogs)
                .save(writer);

        addRecipes(writer);
    }

    /**
     * 注册方块 Tag 的 DataProvider
     * <p>应当在 {@link IntrinsicHolderTagsProvider#addTags(HolderLookup.Provider)} 或与之等效的方法中调用</p>
     */
    public void addBlockTags(IntrinsicHolderTagsProvider<Block> provider) {
        IIntrinsicHolderTagsProvider<Block> p = (IIntrinsicHolderTagsProvider<Block>) provider;
        p.sinocoreTag(tree.tagLogs).add(tree.log(), tree.strippedLog(), tree.wood(), tree.strippedLog());
        p.sinocoreTag(BlockTags.SAPLINGS).add(tree.sapling());
        p.sinocoreTag(BlockTags.LOGS).addTag(tree.tagLogs);
        p.sinocoreTag(BlockTags.LOGS_THAT_BURN).addTag(tree.tagLogs);
        p.sinocoreTag(BlockTags.FLOWER_POTS).add(tree.pottedSapling());
        p.sinocoreTag(BlockTags.LEAVES).add(tree.leaves());
    }

    /**
     * 注册物品 Tag 的 DataProvider
     * <p>应当在 {@link IntrinsicHolderTagsProvider#addTags(HolderLookup.Provider)} 或与之等效的方法中调用</p>
     */
    public void addItemTags(IntrinsicHolderTagsProvider<Item> provider) {
        IIntrinsicHolderTagsProvider<Item> p = (IIntrinsicHolderTagsProvider<Item>) provider;
        p.sinocoreTag(tree.tagItemLogs)
                .add(tree.log().asItem(), tree.strippedLog().asItem(), tree.wood().asItem(), tree.strippedLog().asItem());
        p.sinocoreTag(ItemTags.SAPLINGS).add(tree.sapling().asItem());
        p.sinocoreTag(ItemTags.LOGS_THAT_BURN).addTag(tree.tagItemLogs);
        p.sinocoreTag(ItemTags.LEAVES).add(tree.leaves().asItem());
    }

    /**
     * 注册方块的掉落 DataProvider
     * <p>在 {@link LootTableProvider#getTables()} 方法中调用</p>
     *
     * @param writer 该方法用于处理接收到的
     */
    public TreeBlockLoot addLoots(Consumer<LootTableProvider.SubProviderEntry> writer) {
        TreeBlockLoot loot = new TreeBlockLoot(tree);
        writer.accept(new LootTableProvider.SubProviderEntry(() -> loot, LootContextParamSets.BLOCK));
        return loot;
    }
}
