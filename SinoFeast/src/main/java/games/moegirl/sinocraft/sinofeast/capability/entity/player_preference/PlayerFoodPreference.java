package games.moegirl.sinocraft.sinofeast.capability.entity.player_preference;

import games.moegirl.sinocraft.sinofeast.SFConstants;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTastes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class PlayerFoodPreference implements IPlayerFoodPreference {
    private FoodTaste prefer;
    private FoodTaste like;
    private FoodTaste dislike;

    public PlayerFoodPreference() {
    }

    @Override
    public void randomPreference() {
        prefer = FoodTastes.getInstance().randomPrefer(List.of());
        like = FoodTastes.getInstance().randomLike(List.of(prefer));
        dislike = FoodTastes.getInstance().randomDislike(List.of(prefer, like));
    }

    @Override
    public FoodTaste getPrefer() {
        return prefer;
    }

    @Override
    public boolean isPrefer(FoodTaste advancedTaste) {
        return prefer == advancedTaste;
    }

    @Override
    public void setPrefer(FoodTaste advancedTaste) {
        prefer = advancedTaste;
    }

    @Override
    public FoodTaste getLike() {
        return like;
    }

    @Override
    public boolean isLike(FoodTaste taste) {
        return like == taste;
    }

    @Override
    public void setLike(FoodTaste taste) {
        like = taste;
    }

    @Override
    public FoodTaste getDislike() {
        return dislike;
    }

    @Override
    public boolean isDislike(FoodTaste taste) {
        return dislike == taste;
    }

    @Override
    public void setDislike(FoodTaste taste) {
        dislike = taste;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();

        tag.putString(SFConstants.TAG_PREFER_TASTE_NAME, prefer.getKey().toString());
        tag.putString(SFConstants.TAG_LIKE_TASTE_NAME, like.getKey().toString());
        tag.putString(SFConstants.TAG_DISLIKE_TASTE_NAME, dislike.getKey().toString());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        var preferString = tag.getString(SFConstants.TAG_PREFER_TASTE_NAME);
        if (!preferString.isBlank()) {
            prefer = FoodTastes.getInstance().getTaste(new ResourceLocation(preferString));
        }

        var likeString = tag.getString(SFConstants.TAG_LIKE_TASTE_NAME);
        if (!likeString.isBlank()) {
            like = FoodTastes.getInstance().getTaste(new ResourceLocation(likeString));
        }

        var dislikeString = tag.getString(SFConstants.TAG_DISLIKE_TASTE_NAME);
        if (!dislikeString.isBlank()) {
            dislike = FoodTastes.getInstance().getTaste(new ResourceLocation(dislikeString));
        }
    }
}
