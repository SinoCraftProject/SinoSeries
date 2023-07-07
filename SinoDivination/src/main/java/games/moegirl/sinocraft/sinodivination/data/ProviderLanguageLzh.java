package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.lang.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.data.DataGenerator;

class ProviderLanguageLzh extends AbstractLanguageProvider {

    public ProviderLanguageLzh(DataGenerator generator) {
        super(generator.getPackOutput(), SinoDivination.MODID, "lzh");
    }

    @Override
    public void translate() {
        // todo existed lang at lang_map/map.json

        // verify
        SDBlocks.REGISTRY.getEntries().forEach(entry -> verifyKey(entry.get().getDescriptionId(), "block"));
        SDItems.REGISTRY.getEntries().forEach(entry -> verifyKey(entry.get().getDescriptionId(), "item"));
        SDLangKeys.TRANSLATION_KEYS.forEach(key -> verifyKey(key, "other"));
    }
}
