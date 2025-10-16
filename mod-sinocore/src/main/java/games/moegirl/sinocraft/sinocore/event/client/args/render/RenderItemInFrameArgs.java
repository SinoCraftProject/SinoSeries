package games.moegirl.sinocraft.sinocore.event.client.args.render;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.event.CancellableArgsBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;

public class RenderItemInFrameArgs extends CancellableArgsBase {
    private final ItemStack itemStack;
    private final ItemFrame itemFrameEntity;
    private final ItemFrameRenderer<?> renderer;
    private final PoseStack poseStack;
    private final MultiBufferSource multiBufferSource;
    private final int packedLight;

    public RenderItemInFrameArgs(ItemStack itemStack, ItemFrame itemFrameEntity, ItemFrameRenderer<?> renderer,
                                 PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        this.itemStack = itemStack;
        this.itemFrameEntity = itemFrameEntity;
        this.renderer = renderer;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
        this.packedLight = packedLight;
    }

    public ItemStack itemStack() {
        return itemStack;
    }

    public ItemFrame itemFrameEntity() {
        return itemFrameEntity;
    }

    public ItemFrameRenderer<?> renderer() {
        return renderer;
    }

    public PoseStack poseStack() {
        return poseStack;
    }

    public MultiBufferSource multiBufferSource() {
        return multiBufferSource;
    }

    public int packedLight() {
        return packedLight;
    }
}
