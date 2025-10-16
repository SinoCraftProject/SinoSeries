package games.moegirl.sinocraft.sinocore.data.gen.neoforge.model;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.data.gen.model.IItemModelBuilder;
import games.moegirl.sinocraft.sinocore.data.gen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.utility.ISelf;
import games.moegirl.sinocraft.sinocore.utility.IWrapper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import org.jetbrains.annotations.NotNull;

public class NeoForgeItemModelBuilderWrapper extends NeoForgeModelBuilderWrapper<ItemModelBuilder, NeoForgeItemModelBuilderWrapper>
        implements IItemModelBuilder<NeoForgeItemModelBuilderWrapper> {

    public NeoForgeItemModelBuilderWrapper(ItemModelBuilder modelBuilder) {
        super(modelBuilder);
    }

    @Override
    public IOverrideBuilder<NeoForgeItemModelBuilderWrapper> override() {
        return new ForgeOverrideBuilderWrapper(modelFile.override(), this);
    }

    @Override
    public IOverrideBuilder<NeoForgeItemModelBuilderWrapper> override(int index) {
        return new ForgeOverrideBuilderWrapper(modelFile.override(), this);
    }

    @Override
    public JsonObject toJson() {
        return modelFile.toJson();
    }

    @Override
    public @NotNull ISelf<? extends NeoForgeItemModelBuilderWrapper> newWrapper(ItemModelBuilder object) {
        return new NeoForgeItemModelBuilderWrapper(object);
    }

    record ForgeOverrideBuilderWrapper(ItemModelBuilder.OverrideBuilder builder,
                                       NeoForgeItemModelBuilderWrapper parent)
            implements IOverrideBuilder<NeoForgeItemModelBuilderWrapper>, IWrapper<ItemModelBuilder.OverrideBuilder, ForgeOverrideBuilderWrapper> {

        @Override
        public IOverrideBuilder<NeoForgeItemModelBuilderWrapper> model(IModelFile model) {
            if (model instanceof NeoForgeModelBuilderWrapper<?, ?> wrapper) {
                return reWrapper(builder.model(wrapper.getOrigin()));
            }

            throw new IllegalStateException("Can't case " + model + " to ForgeModelBuilderWrapper");
        }

        @Override
        public IOverrideBuilder<NeoForgeItemModelBuilderWrapper> predicate(ResourceLocation key, float value) {
            return reWrapper(builder.predicate(key, value));
        }

        @Override
        public NeoForgeItemModelBuilderWrapper end() {
            return parent.reWrapper(builder.end());
        }

        @Override
        public ItemModelBuilder.OverrideBuilder getOrigin() {
            return builder;
        }

        @Override
        public ISelf<? extends ForgeOverrideBuilderWrapper> newWrapper(ItemModelBuilder.OverrideBuilder object) {
            return new ForgeOverrideBuilderWrapper(object, parent);
        }
    }
}
