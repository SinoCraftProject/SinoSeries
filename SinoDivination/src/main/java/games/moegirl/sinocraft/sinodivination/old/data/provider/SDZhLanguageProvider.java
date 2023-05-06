package games.moegirl.sinocraft.sinodivination.old.data.provider;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class SDZhLanguageProvider extends AbstractLanguageProvider {

    public SDZhLanguageProvider(DataGenerator generator) {
        super(generator.getPackOutput(), SinoDivination.MODID, "en_us");
    }

    @Override
    public void translate() {
        // group
        addTab(SinoDivination.TAB.name(), "华夏九筮");
        // blocks
        addBlock(SDBlocks.ORE_JADE, "玉矿石");
        addBlock(SDBlocks.ORE_NITER, "硝石矿石");
        addBlock(SDBlocks.ORE_SULPHUR, "硫磺矿石");
        addBlock(SDBlocks.COTINUS_CHEST, "无患木匣");
        addBlock(SDBlocks.JUJUBE_CHEST, "枣木匣");
        addBlock(SDBlocks.SOPHORA_CHEST, "槐木匣");
        addBlock(SDBlocks.WORMWOOD, "艾草");
        addBlock(SDBlocks.GARLIC, "大蒜");
        addBlock(SDBlocks.RICE, "糯稻");
        addBlock(SDBlocks.LUCID_GANODERMA, "灵芝");
        addBlock(SDBlocks.REHMANNIA, "地黄");
        addBlock(SDBlocks.DRAGONLIVER_MELON, "龙肝瓜");
        addBlock(SDBlocks.SESAME, "巨胜");
        addBlock(SDBlocks.ZHU_CAO, "朱草");
        addBlock(SDBlocks.BRIGHT_STEM_GRASS, "明茎草");
        addBlock(SDBlocks.BELLOWS, "风箱");
        addBlock(SDBlocks.KETTLE_POT, "釜锅");
        addBlock(SDBlocks.SILKWORM_PLAQUE, "蚕匾");
        addBlock(SDBlocks.TRIPOD, "鼎");
        addBlock(SDBlocks.ALTAR, "祭坛");
        addBlock(SDBlocks.CARVING_TABLE, "雕刻台");
        // item
        addItem(SDItems.JADE, "玉石");
        addItem(SDItems.NITER, "硝石");
        addItem(SDItems.SULPHUR, "硫磺");
        addItem(SDItems.STICK_COTINUS, "鬼杀棒");
        addItem(SDItems.CHANGE_SOUP, "变荑汤");
        addItem(SDItems.STICK_JUJUBE, "神力棒");
        addItem(SDItems.LIFE_SYMBOL, "命符");
        addItem(SDItems.JUJUBE, "枣");
        addItem(SDItems.STICK_SOPHORA, "御鬼棒");
        addItem(SDItems.SEED_WORMWOOD, "艾草种子");
        addItem(SDItems.WORMWOOD_LEAF, "艾草叶");
        addItem(SDItems.MOXIBUSTION, "艾灸");
        addItem(SDItems.SEED_GARLIC, "大蒜种子");
        addItem(SDItems.GARLIC, "大蒜");
        addItem(SDItems.RICE, "稻米");
        addItem(SDItems.SEED_RICE, "糯稻种子");
        addItem(SDItems.REHMANNIA, "地黄");
        addItem(SDItems.SEED_REHMANNIA, "地黄种子");
        addItem(SDItems.DRAGONLIVER_MELON, "龙肝瓜");
        addItem(SDItems.SEED_DRAGONLIVER, "龙肝瓜种子");
        addItem(SDItems.SESAME, "芝麻");
        addItem(SDItems.SEED_SESAME, "巨胜种子");
        addItem(SDItems.SILKWORM_BABY, "蚕宝宝");
        addItem(SDItems.HOOK, "钩棍");
        addItem(SDItems.SILK, "丝");
        addItem(SDItems.CANG_BI, "苍壁");
        addItem(SDItems.HUANG_CONG, "黄琮");
        addItem(SDItems.QING_GUI, "青珪");
        addItem(SDItems.CHI_ZHANG, "赤璋");
        addItem(SDItems.BAI_HU, "白琥");
        addItem(SDItems.XUAN_HUANG, "玄璜");
        addItem(SDItems.COPPER_GOBLET, "铜爵");
        addItem(SDItems.COPPER_DAGGER_AXE, "铜戈");
        addItem(SDItems.COPPER_MIRROR, "铜镜");
        addItem(SDItems.COPPER_MASK, "铜面");
        addItem(SDItems.COPPER_LAMP, "铜镫");
        addItem(SDItems.COPPER_BEAST, "铜兽");
        addItem(SDItems.STICK_RICE, "糯米饭");
        // single key
        add(SDLangKeys.CARVING_TABLE_TITLE, "雕刻台");
        add(SDLangKeys.SYMBOL_DATE, "生辰八字：");
        add(SDLangKeys.SYMBOL_NAME, "姓名：");
        add(SDLangKeys.SILKWORM_PLAGUE_TITLE, "蚕匾");
        add(SDLangKeys.JEI_RECIPE_CHANGE_SOUP, "变荑汤");
        add(SDLangKeys.JEI_RECIPE_CARVING_TABLE, "雕刻台");
        add(SDLangKeys.JEI_RECIPE_KETTLE_POT, "釜锅");
        add(SDLangKeys.TOP_BIRTHDAY, "生辰八字(%s): %s");
        add(SDLangKeys.TOP_BIRTHDAY_NO, "生辰八字：无记录");
        add(SDLangKeys.TOP_BLOCK_OWNER, "所有者: %s");
        add(SDLangKeys.TOP_BLOCK_ALLOWED, "允许玩家(%d)");

        verify();
    }

    private void verify() {
        for (RegistryObject<Block> entry : SDBlocks.REGISTRY.getEntries()) {
            verify(entry.get().getDescriptionId(), "block", entry.getId());
        }
        for (RegistryObject<Item> entry : SDItems.REGISTRY.getEntries()) {
            verify(entry.get().getDescriptionId(), "item", entry.getId());
        }
        for (String key : SDLangKeys.TRANSLATION_KEYS) {
            verify(key, "other", key);
        }
    }

    private void verify(String languageId, String type, Object name) {
//        boolean hasEn = enData.containsKey(languageId) || childEnData.containsKey(languageId);
//        boolean hasZh = zhData.containsKey(languageId) || childZhData.containsKey(languageId);
//        if (!hasEn && !hasZh) {
//            LOGGER.warn("Lost language for {} {}", type, name);
//        } else if (!hasEn) {
//            LOGGER.warn("Lost english language for {} {}", type, name);
//        } else if (!hasZh) {
//            LOGGER.warn("Lost chinese language for {} {}", type, name);
//        }
    }
}
