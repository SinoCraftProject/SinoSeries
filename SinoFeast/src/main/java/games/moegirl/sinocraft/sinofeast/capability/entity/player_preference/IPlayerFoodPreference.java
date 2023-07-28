package games.moegirl.sinocraft.sinofeast.capability.entity.player_preference;

import games.moegirl.sinocraft.sinofeast.data.food.taste.FoodTaste;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerFoodPreference extends INBTSerializable<CompoundTag> {
    void randomPreference();

    boolean isPrefer(FoodTaste advancedTaste);
    void setPrefer(FoodTaste advancedTaste);

    boolean isLike(FoodTaste taste);
    void setLike(FoodTaste taste);

    boolean isDislike(FoodTaste taste);
    void setDislike(FoodTaste taste);
}
