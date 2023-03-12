package games.moegirl.sinocraft.sinocore.mixin;

import games.moegirl.sinocraft.sinocore.mixin_inter.IIntrinsicHolderTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IntrinsicHolderTagsProvider.class)
public abstract class MixinIntrinsicHolderTagsProvider<T> implements IIntrinsicHolderTagsProvider<T> {

    @Shadow protected abstract IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> tag(TagKey<T> tag);

    @Override
    public IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> sinocoreTag(TagKey<T> tag) {
        return tag(tag);
    }
}
