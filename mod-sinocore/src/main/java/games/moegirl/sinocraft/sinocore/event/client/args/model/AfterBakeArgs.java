package games.moegirl.sinocraft.sinocore.event.client.args.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class AfterBakeArgs {
    private final ModelBakery modelBakery;
    private final ModelResourceLocation id;
    private BakedModel model;

    public AfterBakeArgs(ModelBakery modelBakery, ModelResourceLocation id, BakedModel model) {
        this.modelBakery = modelBakery;
        this.id = id;
        this.model = model;
    }

    public ModelBakery modelBakery() {
        return modelBakery;
    }

    public ModelResourceLocation id() {
        return id;
    }

    public BakedModel model() {
        return model;
    }

    public void setModel(BakedModel model) {
        this.model = model;
    }
}
