package games.moegirl.sinocraft.sinodivination.old.data.provider;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.old.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.old.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import games.moegirl.sinocraft.sinodivination.old.util.StringUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class SDEnLanguageProvider extends AbstractLanguageProvider {

    public SDEnLanguageProvider(DataGenerator generator) {
        super(generator.getPackOutput(), SinoDivination.MODID, "en_us");
    }

    @Override
    public void translate() {
        // group
        addTab(SinoDivination.TAB.name(), "SinoDivination");
        // blocks
        addBlock(SDBlocks.ORE_JADE, "Jade Ore");
        addBlock(SDBlocks.ORE_NITER, "Niter Ore");
        addBlock(SDBlocks.ORE_SULPHUR, "Sulphur Ore");
        addBlock(SDBlocks.COTINUS_CHEST, "Cotinus Chest");
        addBlock(SDBlocks.JUJUBE_CHEST, "Jujube Chest");
        addBlock(SDBlocks.SOPHORA_CHEST, "Sophora Chest");
        addBlock(SDBlocks.WORMWOOD, "Wormwood");
        addBlock(SDBlocks.GARLIC, "Garlic");
        addBlock(SDBlocks.RICE, "Glutinous Rise");
        addBlock(SDBlocks.LUCID_GANODERMA, "Ganoderma Lucidum");
        addBlock(SDBlocks.REHMANNIA, "Rehmannia Glutinosa");
        addBlock(SDBlocks.DRAGONLIVER_MELON, "Longan Melon");
        addBlock(SDBlocks.SESAME, "Jusheng");
        addBlock(SDBlocks.ZHU_CAO, "Zhu Cao");
        addBlock(SDBlocks.BRIGHT_STEM_GRASS, "Bright Stem Grass");
        addBlock(SDBlocks.BELLOWS, "Bellows");
        addBlock(SDBlocks.KETTLE_POT, "Kettle Pot");
        addBlock(SDBlocks.SILKWORM_PLAQUE, "Silkworm Plaque");
        addBlock(SDBlocks.TRIPOD, "鼎");
        addBlock(SDBlocks.ALTAR, "祭坛");
        addBlock(SDBlocks.CARVING_TABLE, "雕刻台");
        // item
        addItem(SDItems.JADE, "Jade");
        addItem(SDItems.NITER, "Niter");
        addItem(SDItems.SULPHUR, "Sulphur");
        addItem(SDItems.STICK_COTINUS, "Ghost Killing Stick");
        addItem(SDItems.CHANGE_SOUP, "Changing Seeding Decoction");
        addItem(SDItems.STICK_JUJUBE, "Divine Power Stick");
        addItem(SDItems.LIFE_SYMBOL, "Life Symbol");
        addItem(SDItems.JUJUBE, "Jujube");
        addItem(SDItems.STICK_SOPHORA, "Ghost Guard Stick");
        addItem(SDItems.SEED_WORMWOOD, "Argy Wormwood Seed");
        addItem(SDItems.WORMWOOD_LEAF, "Argy Wormwood Leaves");
        addItem(SDItems.MOXIBUSTION, "Moxibustion");
        addItem(SDItems.SEED_GARLIC, "Garlic Seed");
        addItem(SDItems.GARLIC, "Garlic");
        addItem(SDItems.RICE, "Rice");
        addItem(SDItems.SEED_RICE, "Glutinous Rice Seed");
        addItem(SDItems.REHMANNIA, "Rehmannia Glutinosa");
        addItem(SDItems.SEED_REHMANNIA, "Rehmannia Seed");
        addItem(SDItems.DRAGONLIVER_MELON, "Longan Melon");
        addItem(SDItems.SEED_DRAGONLIVER, "Longan Melon Seed");
        addItem(SDItems.SESAME, "Sesame");
        addItem(SDItems.SEED_SESAME, "Jusheng Seed");
        addItem(SDItems.SILKWORM_BABY, "Silkworm Baby");
        addItem(SDItems.HOOK, "Hook");
        addItem(SDItems.SILK, "Silk");
        addItem(SDItems.CANG_BI, "Cang Bi");
        addItem(SDItems.HUANG_CONG, "Huang Cong");
        addItem(SDItems.QING_GUI, "Qing Gui");
        addItem(SDItems.CHI_ZHANG, "Chi Zhang");
        addItem(SDItems.BAI_HU, "Bai Hu");
        addItem(SDItems.XUAN_HUANG, "Xuan Huang");
        addItem(SDItems.COPPER_GOBLET);
        addItem(SDItems.COPPER_DAGGER_AXE, "Copper Dagger-axe");
        addItem(SDItems.COPPER_MIRROR);
        addItem(SDItems.COPPER_MASK);
        addItem(SDItems.COPPER_LAMP);
        addItem(SDItems.COPPER_BEAST);
        addItem(SDItems.STICK_RICE);
        // single key
        add(SDLangKeys.CARVING_TABLE_TITLE, "Carving Table");
        add(SDLangKeys.SYMBOL_DATE, "Birthday");
        add(SDLangKeys.SYMBOL_NAME, "Name");
        add(SDLangKeys.SILKWORM_PLAGUE_TITLE, "Silkworm Plague");
        add(SDLangKeys.JEI_RECIPE_CHANGE_SOUP, "Change Soup");
        add(SDLangKeys.JEI_RECIPE_CARVING_TABLE, "Carving Table");
        add(SDLangKeys.JEI_RECIPE_KETTLE_POT, "Kettle Pot");
        add(SDLangKeys.TOP_BIRTHDAY, "Birthday(%s): %s");
        add(SDLangKeys.TOP_BIRTHDAY_NO, "Birthday: No Record");
        add(SDLangKeys.TOP_BLOCK_OWNER, "Owner: %s");
        add(SDLangKeys.TOP_BLOCK_ALLOWED, "Allowed Player(%d)");

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

    private void addItem(RegistryObject<? extends Item> item) {
        String enName = StringUtils.toPascalName(item.getId().getPath(), true);
        super.addItem(item, enName);
    }

    private void addBlock(RegistryObject<? extends Block> block) {
        String enName = StringUtils.toPascalName(block.getId().getPath(), true);
        super.addBlock(block, enName);
    }
}
