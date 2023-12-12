package games.moegirl.sinocraft.sinofoundation.data.gen;

import games.moegirl.sinocraft.sinofoundation.SFDDamages;
import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.world.SFDFeatures;
import games.moegirl.sinocraft.sinofoundation.world.SFDPlacements;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 世界生成
 * @author luqin2007
 */
public class SFDDatapackProviderStep1 extends DatapackBuiltinEntriesProvider {

    public SFDDatapackProviderStep1(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, SFDDamages::bootstrap) // 伤害类型
                        .add(Registries.CONFIGURED_FEATURE, SFDFeatures::bootstrap) // 世界生成（局部）
                        .add(Registries.PLACED_FEATURE, SFDPlacements::bootstrap) // 世界生成
                , Set.of(SinoFoundation.MODID));
    }

    @Override
    public String getName() {
        return SinoFoundation.MODID + ": Damage and Feature";
    }
}
