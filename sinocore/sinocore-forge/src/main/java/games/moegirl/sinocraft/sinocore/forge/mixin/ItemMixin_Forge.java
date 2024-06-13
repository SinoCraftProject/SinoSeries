package games.moegirl.sinocraft.sinocore.forge.mixin;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.injectable.ISinoItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class ItemMixin_Forge implements ISinoItem {

    @Shadow(remap = false)
    private Object renderProperties;

    @Inject(at = @At(value = "TAIL"), method = "initClient", remap = false)
    private void afterInitClient(CallbackInfo ci) {
        var renderer = sino$getCustomRender();
        if (renderer != null) {
            if (renderProperties instanceof IClientItemExtensions ext) {
                renderProperties = new IClientItemExtensions() {
                    @Override
                    public @Nullable Font getFont(ItemStack stack, FontContext context) {
                        return ext.getFont(stack, context);
                    }

                    @Nullable
                    @Override
                    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                        return ext.getArmPose(entityLiving, hand, itemStack);
                    }

                    @Override
                    public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                        return ext.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
                    }

                    @Override
                    public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                        return ext.getGenericArmorModel(livingEntity, itemStack, equipmentSlot, original);
                    }

                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return renderer;
                    }

                    @Override
                    public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
                        ext.renderHelmetOverlay(stack, player, width, height, partialTick);
                    }
                };
            } else {
                renderProperties = new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return renderer;
                    }
                };
            }
        }
    }
}
