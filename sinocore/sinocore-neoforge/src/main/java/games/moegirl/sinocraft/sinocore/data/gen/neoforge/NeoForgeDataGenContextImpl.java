package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.IDataGenContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public record NeoForgeDataGenContextImpl(GatherDataEvent event,
                                         CompletableFuture<HolderLookup.Provider> getRegistries)
        implements IDataGenContext {

    @Override
    public String getModId() {
        return event.getModContainer().getModId();
    }

    @Override
    public PackOutput getOutput() {
        return event.getGenerator().getPackOutput();
    }

    public ExistingFileHelper getExistingFileHelper() {
        return event.getExistingFileHelper();
    }
}
