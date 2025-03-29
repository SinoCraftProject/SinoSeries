package games.moegirl.sinocraft.sinocore.data.gen.neoforge;

import games.moegirl.sinocraft.sinocore.data.gen.DataGenContext;
import games.moegirl.sinocraft.sinocore.data.gen.DataGenerator;
import games.moegirl.sinocraft.sinocore.utility.neoforge.ModBusHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.Consumer;

public class NeoForgeDataGenerator extends DataGenerator implements Consumer<GatherDataEvent> {

    public NeoForgeDataGenerator(String modId) {
        ModBusHelper.getModBus(modId).addListener(this);
    }

    @Override
    public void accept(DataGenContext context) {
        var generator = context.getGenerator();
        providers.forEach(builder -> generator.addProvider(true, builder.apply(context)));
    }

    @Override
    public void accept(GatherDataEvent event) {
        var context = new NeoForgeDataGenContext(event);
        accept(context);
    }
}
