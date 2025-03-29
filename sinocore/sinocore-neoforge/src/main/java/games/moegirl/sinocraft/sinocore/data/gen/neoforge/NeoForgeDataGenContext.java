package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class NeoForgeDataGenContext extends DataGenContext {
    private final ExistingFileHelper exHelper;

    public NeoForgeDataGenContext(GatherDataEvent event) {
        super(event.getModContainer().getModId(), event.getGenerator(), event.getGenerator().getPackOutput(), event.getLookupProvider());
        this.exHelper = event.getExistingFileHelper();
    }

    @Override
    public ExistingFileHelper getExistingFileHelper() {
        return exHelper;
    }
}
