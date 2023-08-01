package games.moegirl.sinocraft.sinofeast.capability.entity.player_preference;

import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerFoodPreference extends INBTSerializable<CompoundTag> {
    void randomPreference();

    FoodTaste getPrefer();
    boolean isPrefer(FoodTaste advancedTaste);
    void setPrefer(FoodTaste advancedTaste);

    FoodTaste getLike();
    boolean isLike(FoodTaste taste);
    void setLike(FoodTaste taste);

    FoodTaste getDislike();
    boolean isDislike(FoodTaste taste);
    void setDislike(FoodTaste taste);
}
