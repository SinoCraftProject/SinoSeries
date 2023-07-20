package games.moegirl.sinocraft.sinodivination.data.gen.lang;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinocore.utility.NameUtils;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.data.SDLangKeys;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ProviderLanguageEn extends AbstractLanguageProvider {

    public ProviderLanguageEn(DataGenerator generator) {
        super(generator.getPackOutput(), SinoDivination.MODID, "en_us");
    }

    @Override
    public void translate() {
        // blocks
        addBlock(SDBlocks.KETTLE_POT);
        addBlock(SDBlocks.BELLOWS);
        addBlock(SDBlocks.SILKWORM_PLAQUE);
        addBlock(SDBlocks.TRIPOD);
        addBlock(SDBlocks.ALTAR);
        addBlock(SDBlocks.CARVING_TABLE);
        addBlock(SDBlocks.LUCID_GANODERMA, "Ganoderma Lucidum");
        addBlock(SDBlocks.REHMANNIA, "Rehmannia Glutinosa");
        addBlock(SDBlocks.DRAGONLIVER_MELON, "Longan Melon");
        addBlock(SDBlocks.ZHU_CAO);
        addBlock(SDBlocks.BRIGHT_STEM_GRASS);
        addBlock(SDBlocks.COTINUS_CHEST);
        addBlock(SDBlocks.COTINUS_TRAPPED_CHEST);
        addBlock(SDBlocks.SOPHORA_CHEST);
        addBlock(SDBlocks.SOPHORA_TRAPPED_CHEST);
        // item
        addItem(SDItems.CHANGE_SOUP, "Changing Seeding Decoction");
        addItem(SDItems.LIFE_SYMBOL, "Life Symbol");
        addItem(SDItems.MOXIBUSTION, "Moxibustion");
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
        addItem(SDItems.REHMANNIA, "Rehmannia Glutinosa");
        addItem(SDItems.SEED_REHMANNIA, "Rehmannia Seed");
        addItem(SDItems.DRAGONLIVER_MELON, "Longan Melon");
        addItem(SDItems.SEED_DRAGONLIVER, "Longan Melon Seed");
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
