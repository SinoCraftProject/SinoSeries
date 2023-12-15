package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinofoundation.data.gen.loot_modifier.SeedDropModifier;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class SFDDropModifierProvider extends GlobalLootModifierProvider {

    public SFDDropModifierProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void start() {
        add("seeds", SeedDropModifier.defaultModifier(0.15f));
    }
}
