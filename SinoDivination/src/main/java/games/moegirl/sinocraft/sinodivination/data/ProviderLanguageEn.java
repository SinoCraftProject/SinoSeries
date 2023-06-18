package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

class ProviderLanguageEn extends AbstractLanguageProvider {

    public ProviderLanguageEn(DataGenerator generator) {
        super(generator.getPackOutput(), SinoDivination.MODID, "en_us");
    }

    @Override
    public void translate() {
        // blocks
        addBlock(SDBlocks.KETTLE_POT, "Kettle Pot");
        addBlock(SDBlocks.WORMWOOD, "Wormwood");
        addBlock(SDBlocks.RICE, "Glutinous Rise");
        addBlock(SDBlocks.LUCID_GANODERMA, "Ganoderma Lucidum");
        addBlock(SDBlocks.REHMANNIA, "Rehmannia Glutinosa");
        addBlock(SDBlocks.DRAGONLIVER_MELON, "Longan Melon");
        addBlock(SDBlocks.SESAME, "Jusheng");
        addBlock(SDBlocks.ZHU_CAO, "Zhu Cao");
        addBlock(SDBlocks.BRIGHT_STEM_GRASS, "Bright Stem Grass");
        addBlock(SDBlocks.BELLOWS, "Bellows");
        addBlock(SDBlocks.SILKWORM_PLAQUE, "Silkworm Plaque");
        addBlock(SDBlocks.TRIPOD);
        addBlock(SDBlocks.ALTAR);
        addBlock(SDBlocks.CARVING_TABLE);
        // item
        addItem(SDItems.CHANGE_SOUP, "Changing Seeding Decoction");
        addItem(SDItems.LIFE_SYMBOL, "Life Symbol");
        addItem(SDItems.SEED_WORMWOOD, "Argy Wormwood Seed");
        addItem(SDItems.WORMWOOD_LEAF, "Argy Wormwood Leaves");
        addItem(SDItems.MOXIBUSTION, "Moxibustion");
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
        addItem(SDItems.STICK_COTINUS, "Ghost Killing Stick");
        addItem(SDItems.STICK_JUJUBE, "Divine Power Stick");
        addItem(SDItems.STICK_SOPHORA, "Ghost Guard Stick");
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

        // verify
        SDBlocks.REGISTRY.getEntries().forEach(entry -> verifyKey(entry.get().getDescriptionId(), "block"));
        SDItems.REGISTRY.getEntries().forEach(entry -> verifyKey(entry.get().getDescriptionId(), "item"));
        SDLangKeys.TRANSLATION_KEYS.forEach(key -> verifyKey(key, "other"));
    }

    private void addItem(RegistryObject<? extends Item> item) {
        addItem(item, NameUtils.toPascalName(item.getId().getPath(), true));
    }

    private void addBlock(RegistryObject<? extends Block> block) {
        addBlock(block, NameUtils.toPascalName(block.getId().getPath(), true));
    }
}
