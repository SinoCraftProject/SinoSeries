package games.moegirl.sinocraft.sinocore.tree;

import games.moegirl.sinocraft.sinocore.old.data.base.WarnBlockStateProvider;
import games.moegirl.sinocraft.sinocore.old.data.base.WarnItemModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TreeDataProvider {

    private TreeRegister register;
    private String modid;

    public void register(IEventBus bus, TreeRegister register) {
        this.register = register;
        this.modid = register.tree().name().getNamespace();
        bus.register(this);
    }

    @SubscribeEvent
    public void onGatherData(GatherDataEvent event) {
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        generator.addProvider(true, new LanguageProvider(output, modid, "en_us") {
            @Override
            protected void addTranslations() {
                register.addLanguagesEn(this);
            }
        });
        generator.addProvider(true, new LanguageProvider(output, modid, "zh_cn") {
            @Override
            protected void addTranslations() {
                register.addLanguagesZh(this, "测试树");
            }
        });
        generator.addProvider(true, new WarnBlockStateProvider(output, modid, helper) {
            @Override
            protected void registerStatesAndModels() {
                register.addBlockModels(this);
            }
        });
        generator.addProvider(true, new WarnItemModelProvider(output, modid, helper) {
            @Override
            protected void registerModels() {
                register.addItemModels(this);
            }
        });
        generator.addProvider(true, new RecipeProvider(output) {
            @Override
            protected void buildRecipes(Consumer<FinishedRecipe> writer) {
                register.addRecipes(writer);
            }
        });
        IntrinsicHolderTagsProvider<Block> blocks;
        generator.addProvider(true, blocks = new BlockTagsProvider(output, provider, modid, helper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                register.addBlockTags(this);
            }
        });
        generator.addProvider(true, new ItemTagsProvider(output, provider, blocks, modid, helper) {

            @Override
            protected void addTags(HolderLookup.Provider provider) {
                register.addItemTags(this);
            }
        });
        generator.addProvider(true, new LootTableProvider(output, new HashSet<>(), new ArrayList<>()) {
            @Override
            public List<SubProviderEntry> getTables() {
                List<SubProviderEntry> list = new ArrayList<>();
                register.addLoots(list::add);
                return list;
            }
        });
    }
}
