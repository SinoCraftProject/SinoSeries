package games.moegirl.sinocraft.sinodivination.old.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.lang3.ArrayUtils;

public class AltarRecipeBuilder extends SimpleRecipeBuilder<AltarRecipe, AltarRecipeBuilder> {

    private ItemStack result = ItemStack.EMPTY;
    private Ingredient base = Ingredient.EMPTY;
    private Item[] sacrificialVessels = new Item[]{Items.AIR, Items.AIR, Items.AIR, Items.AIR};
    private int tick = 40;

    public AltarRecipeBuilder(ResourceLocation id) {
        super(id);
    }

    public AltarRecipeBuilder sacrificialVessel(Item sacrificialVessel) {
        sacrificialVessels[ArrayUtils.indexOf(sacrificialVessels, Items.AIR)] = sacrificialVessel;
        return this;
    }

    public AltarRecipeBuilder sacrificialVessels(Item i0) {
        return sacrificialVessels(new Item[]{i0, Items.AIR, Items.AIR, Items.AIR});
    }

    public AltarRecipeBuilder sacrificialVessels(Item i0, Item i1) {
        return sacrificialVessels(new Item[]{i0, i1, Items.AIR, Items.AIR});
    }

    public AltarRecipeBuilder sacrificialVessels(Item i0, Item i1, Item i2) {
        return sacrificialVessels(new Item[]{i0, i1, i2, Items.AIR});
    }

    public AltarRecipeBuilder sacrificialVessels(Item i0, Item i1, Item i2, Item i3) {
        return sacrificialVessels(new Item[]{i0, i1, i2, i3});
    }

    public AltarRecipeBuilder sacrificialVessels(Item[] sacrificialVessels) {
        this.sacrificialVessels = sacrificialVessels;
        return this;
    }

    public AltarRecipeBuilder base(Ingredient base) {
        this.base = base;
        return this;
    }

    public AltarRecipeBuilder tick(int tick) {
        this.tick = tick;
        return this;
    }

    public AltarRecipeBuilder result(ItemStack stack) {
        this.result = stack;
        return this;
    }

    @Override
    public AltarRecipe build() {
        return new AltarRecipe(id, sacrificialVessels, base, tick, result);
    }
}
