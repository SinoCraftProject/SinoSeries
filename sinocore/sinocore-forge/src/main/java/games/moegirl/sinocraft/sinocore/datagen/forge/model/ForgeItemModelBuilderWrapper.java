package games.moegirl.sinocraft.sinocore.datagen.forge.model;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.datagen.model.IItemModelBuilder;
import games.moegirl.sinocraft.sinocore.datagen.model.IModelFile;
import games.moegirl.sinocraft.sinocore.utility.ISelf;
import games.moegirl.sinocraft.sinocore.utility.IWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;

public class ForgeItemModelBuilderWrapper extends ForgeModelBuilderWrapper<ItemModelBuilder, ForgeItemModelBuilderWrapper>
        implements IItemModelBuilder<ForgeItemModelBuilderWrapper> {

    public ForgeItemModelBuilderWrapper(ItemModelBuilder modelBuilder) {
        super(modelBuilder);
    }

    @Override
    public IOverrideBuilder<ForgeItemModelBuilderWrapper> override() {
        return new ForgeOverrideBuilderWrapper(modelFile.override(), this);
    }

    @Override
    public IOverrideBuilder<ForgeItemModelBuilderWrapper> override(int index) {
        return new ForgeOverrideBuilderWrapper(modelFile.override(), this);
    }

    @Override
    public JsonObject toJson() {
        return modelFile.toJson();
    }

    @Override
    public ISelf<? extends ForgeItemModelBuilderWrapper> newWrapper(ItemModelBuilder object) {
        return new ForgeItemModelBuilderWrapper(object);
    }

    record ForgeOverrideBuilderWrapper(ItemModelBuilder.OverrideBuilder builder,
                                       ForgeItemModelBuilderWrapper parent)
            implements IOverrideBuilder<ForgeItemModelBuilderWrapper>, IWrapper<ItemModelBuilder.OverrideBuilder, ForgeOverrideBuilderWrapper> {

        @Override
        public IOverrideBuilder<ForgeItemModelBuilderWrapper> model(IModelFile model) {
            if (model instanceof ForgeModelBuilderWrapper<?, ?> wrapper) {
                return reWrapper(builder.model(wrapper.getOrigin()));
            }

            throw new IllegalStateException("Can't case " + model + " to ForgeModelBuilderWrapper");
        }

        @Override
        public IOverrideBuilder<ForgeItemModelBuilderWrapper> predicate(ResourceLocation key, float value) {
            return reWrapper(builder.predicate(key, value));
        }

        @Override
        public ForgeItemModelBuilderWrapper end() {
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
