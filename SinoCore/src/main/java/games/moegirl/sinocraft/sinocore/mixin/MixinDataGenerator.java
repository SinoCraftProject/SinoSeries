package games.moegirl.sinocraft.sinocore.mixin;

import com.google.common.base.Stopwatch;
import games.moegirl.sinocraft.sinocore.data.PostProvider;
import games.moegirl.sinocraft.sinocore.mixin_inter.IDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.TimeUnit;

/**
 * @author luqin2007
 */
@Mixin(DataGenerator.class)
public abstract class MixinDataGenerator implements IDataGenerator {

    @Shadow @Final private static Logger LOGGER;

    @Shadow public abstract <T extends DataProvider> T addProvider(boolean run, T provider);

    private PostProvider sinocorePostProvider = null;

    @Override
    public void sinocoreSetPost(String modid, PackOutput output, ExistingFileHelper helper) {
        if (sinocorePostProvider == null) {
            sinocorePostProvider = new PostProvider(modid, output);
            addProvider(false, sinocorePostProvider);
        }
    }

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/HashCache;purgeStaleAndWrite()V"),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    protected void injectRun(CallbackInfo ci, HashCache hashcache, Stopwatch stopwatch, Stopwatch stopwatch1) {
        if (sinocorePostProvider != null) {
            stopwatch1.start();
            LOGGER.info("Starting post provider: {}", sinocorePostProvider.getName());
            hashcache.applyUpdate(hashcache.generateUpdate(sinocorePostProvider.getName(), sinocorePostProvider::run).join());
            stopwatch1.stop();
            LOGGER.info("All post provider took: {} ms", stopwatch1.elapsed(TimeUnit.MILLISECONDS));
            stopwatch1.reset();
        }
    }
}
