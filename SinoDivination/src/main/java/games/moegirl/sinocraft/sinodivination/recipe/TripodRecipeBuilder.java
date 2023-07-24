package games.moegirl.sinocraft.sinodivination.recipe;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.SimpleRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.lang3.ArrayUtils;

public class TripodRecipeBuilder extends SimpleRecipeBuilder<TripodRecipe, TripodRecipeBuilder> {

    private ItemStack result = ItemStack.EMPTY;
    private Ingredient base = Ingredient.EMPTY;
    private Item[] sacrificialVessels = new Item[]{Items.AIR, Items.AIR, Items.AIR, Items.AIR};
    private int tick = 40;

    public TripodRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public TripodRecipeBuilder sacrificialVessel(Item sacrificialVessel) {
        sacrificialVessels[ArrayUtils.indexOf(sacrificialVessels, Items.AIR)] = sacrificialVessel;
        return this;
    }

    public TripodRecipeBuilder sacrificialVessels(Item i0) {
        return sacrificialVessels(new Item[]{i0, Items.AIR, Items.AIR, Items.AIR});
    }

    public TripodRecipeBuilder sacrificialVessels(Item i0, Item i1) {
        return sacrificialVessels(new Item[]{i0, i1, Items.AIR, Items.AIR});
    }

    public TripodRecipeBuilder sacrificialVessels(Item i0, Item i1, Item i2) {
        return sacrificialVessels(new Item[]{i0, i1, i2, Items.AIR});
    }

    public TripodRecipeBuilder sacrificialVessels(Item i0, Item i1, Item i2, Item i3) {
        return sacrificialVessels(new Item[]{i0, i1, i2, i3});
    }

    public TripodRecipeBuilder sacrificialVessels(Item[] sacrificialVessels) {
        this.sacrificialVessels = sacrificialVessels;
        return this;
    }

    public TripodRecipeBuilder base(Ingredient base) {
        this.base = base;
        return this;
    }

    public TripodRecipeBuilder tick(int tick) {
        this.tick = tick;
        return this;
    }

    public TripodRecipeBuilder result(ItemStack stack) {
        this.result = stack;
        return this;
    }

    @Override
    public TripodRecipe build() {
        return new TripodRecipe(id, sacrificialVessels, base, tick, result);
    }
}
