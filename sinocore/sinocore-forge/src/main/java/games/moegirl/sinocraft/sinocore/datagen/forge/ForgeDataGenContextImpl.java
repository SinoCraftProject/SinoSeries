package games.moegirl.sinocraft.sinocore.datagen.forge;

import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public final class ForgeDataGenContextImpl implements IDataGenContext {
    private final GatherDataEvent event;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    public ForgeDataGenContextImpl(GatherDataEvent event,
                                   CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.event = event;
        this.registriesFuture = registriesFuture;
    }

    @Override
    public String getModId() {
        return event.getModContainer().getModId();
    }

    @Override
    public PackOutput getOutput() {
        return event.getGenerator().getPackOutput();
    }

    @Override
    public CompletableFuture<HolderLookup.Provider> registriesFuture() {
        return registriesFuture;
    }

    public ExistingFileHelper getExistingFileHelper() {
        return event.getExistingFileHelper();
    }

    public GatherDataEvent getEvent() {
        return event;
    }
}
