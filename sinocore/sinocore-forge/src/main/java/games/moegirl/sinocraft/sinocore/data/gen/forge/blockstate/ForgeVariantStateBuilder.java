package games.moegirl.sinocraft.sinocore.data.gen.forge.blockstate;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.data.gen.blockstate.IConfiguredModel;
import games.moegirl.sinocraft.sinocore.data.gen.blockstate.IVariantStateBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

import java.util.function.Function;

public class ForgeVariantStateBuilder implements IVariantStateBuilder {

    private final VariantBlockStateBuilder internal;

    public ForgeVariantStateBuilder(VariantBlockStateBuilder internal) {
        this.internal = internal;
    }

    @Override
    public IVariantStateBuilder addModels(IPartialState state, IConfiguredModel... models) {
        return null;
    }

    @Override
    public IPartialState partialState() {
        return null;
    }

    @Override
    public IVariantStateBuilder forAllStates(Function<BlockState, IConfiguredModel[]> mapper) {
        return null;
    }

    @Override
    public IVariantStateBuilder forAllStatesExcept(Function<BlockState, IConfiguredModel[]> mapper, Property<?>... ignored) {
        return null;
    }

    @Override
    public Block getOwner() {
        return null;
    }

    @Override
    public JsonObject toJson() {
        return null;
    }
}
