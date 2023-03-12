package games.moegirl.sinocraft.sinocore.mixin_inter;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;


public interface IIntrinsicHolderTagsProvider<T> {

    IntrinsicHolderTagsProvider.IntrinsicTagAppender<T> sinocoreTag(TagKey<T> tag);
}
