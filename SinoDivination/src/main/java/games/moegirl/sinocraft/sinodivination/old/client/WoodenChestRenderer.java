package games.moegirl.sinocraft.sinodivination.old.client;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinodivination.old.block.base.WoodenChest;
import games.moegirl.sinocraft.sinodivination.old.blockentity.WoodenChestEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WoodenChestRenderer implements IClientItemExtensions {

    private static final Map<ResourceLocation, EnumMap<ChestType, Material>> NORMAL_MATERIALS = new HashMap<>();
    private static final Map<ResourceLocation, EnumMap<ChestType, Material>> TRAPPED_MATERIALS = new HashMap<>();
    private static final Map<ResourceLocation, WoodenChestRenderer> RENDERERS = new HashMap<>();

    public final WoodenChest chest;
    private final BlockEntityWithoutLevelRenderer item;

    public WoodenChestRenderer(WoodenChest chest) {
        this.chest = chest;
        item = new ItemRenderer(chest);
    }

    public ChestRenderer<WoodenChestEntity> getBlockRenderer(BlockEntityRendererProvider.Context pContext) {
        return new BlockRenderer(pContext);
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return item;
    }

    static class ItemRenderer extends BlockEntityWithoutLevelRenderer {

        private final Lazy<WoodenChestEntity> entity;

        public ItemRenderer(WoodenChest chest) {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
            entity = Lazy.of(() -> new WoodenChestEntity(chest, BlockPos.ZERO, chest.defaultBlockState()));
        }

        @Override
        public void renderByItem(ItemStack item, ItemDisplayContext transformType, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(entity.get(), stack, buffer, light, overlay);
        }
    }

    static class BlockRenderer extends ChestRenderer<WoodenChestEntity> {

        private final boolean xmasTextures;

        public BlockRenderer(BlockEntityRendererProvider.Context pContext) {
            super(pContext);
            Calendar calendar = Calendar.getInstance();
            this.xmasTextures = calendar.get(Calendar.MONTH) + 1 == 12
                    && calendar.get(Calendar.DATE) >= 24
                    && calendar.get(Calendar.DATE) <= 26;
        }

        @Override
        protected Material getMaterial(WoodenChestEntity blockEntity, ChestType chestType) {
            if (xmasTextures) {
                return super.getMaterial(blockEntity, chestType);
            }
            return getOrCreateMaterial(blockEntity.isTrapped, blockEntity.name, chestType);
        }
    }

    public static Material getOrCreateMaterial(boolean trapped, ResourceLocation name, ChestType type) {
        return (trapped ? TRAPPED_MATERIALS : NORMAL_MATERIALS).computeIfAbsent(name, __ -> {
            EnumMap<ChestType, Material> map = new EnumMap<>(ChestType.class);
            map.put(ChestType.LEFT, createChestMaterial(name, "left"));
            map.put(ChestType.RIGHT, createChestMaterial(name, "right"));
            map.put(ChestType.SINGLE, createChestMaterial(name, null));
            return map;
        }).get(type);
    }

    public static Material createChestMaterial(ResourceLocation name, @Nullable String part) {
        StringBuilder rName = new StringBuilder(name.getPath());
        if (part != null) rName.append('_').append(part);
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(name.getNamespace(), "entity/chest/" + rName));
    }

    public static void getAllMaterials(Consumer<Material> pMaterialConsumer) {
        WoodenChest.stream().forEach(chest -> {
            for (ChestType chestType : ChestType.values()) {
                getOrCreateMaterial(chest.isTrapped, chest.name, chestType);
            }
        });
        NORMAL_MATERIALS.forEach((woodType, map) -> map.forEach((chestType, material) -> pMaterialConsumer.accept(material)));
        TRAPPED_MATERIALS.forEach((woodType, map) -> map.forEach((chestType, material) -> pMaterialConsumer.accept(material)));
    }

    public static WoodenChestRenderer get(WoodenChest chest) {
        return RENDERERS.computeIfAbsent(chest.name, k -> new WoodenChestRenderer(chest));
    }
}
