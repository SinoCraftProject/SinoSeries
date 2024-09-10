package games.moegirl.sinocraft.sinobrush.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinocore.utility.data.DataComponentHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record PaperData(int expands) {
    public static final Codec<PaperData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.optionalFieldOf("expands", 0).forGetter(PaperData::expands)
            ).apply(instance, PaperData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, PaperData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, PaperData::expands,
            PaperData::new
    );

    public PaperData() {
        this(0);
    }

    public static final PaperData DEFAULT = new PaperData();

    public static PaperData get(ItemStack stack) {
        return DataComponentHelper.get(stack, SBRDataComponents.PAPER.get(), DEFAULT);
    }

    public static void set(ItemStack stack, PaperData paper) {
        stack.set(SBRDataComponents.PAPER.get(), paper);
    }
}
