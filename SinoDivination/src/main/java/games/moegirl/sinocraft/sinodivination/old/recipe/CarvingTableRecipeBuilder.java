package games.moegirl.sinocraft.sinodivination.old.recipe;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipeBuilder;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CarvingTableRecipeBuilder extends SimpleRecipeBuilder<CarvingTableRecipe, CarvingTableRecipeBuilder> {

    private final String[] patterns = new String[]{"", "", "", ""};
    private final CharList keys = new CharArrayList();
    private final List<Ingredient> ingredients = new ArrayList<>();
    private int linePtr = 0;
    private Ingredient dye = Ingredient.EMPTY;
    private final ItemStack output;

    public CarvingTableRecipeBuilder(ResourceLocation id, ItemStack output) {
        super(id);
        this.output = output;
    }

    public CarvingTableRecipeBuilder(ItemStack output) {
        this(ForgeRegistries.ITEMS.getKey(output.getItem()), output);
    }

    public CarvingTableRecipeBuilder pattern(String pattern) {
        patterns[linePtr++] = pattern;
        return this;
    }

    public CarvingTableRecipeBuilder define(char c, Ingredient ingredient) {
        int i = keys.indexOf(c);
        if (i < 0) {
            keys.add(c);
            ingredients.add(ingredient);
        } else {
            ingredients.set(i, ingredient);
        }
        return self();
    }

    public CarvingTableRecipeBuilder define(char c, ItemLike... items) {
        return define(c, Ingredient.of(items));
    }

    public CarvingTableRecipeBuilder define(char c, Supplier<? extends ItemLike> item) {
        return define(c, Ingredient.of(item.get()));
    }

    public CarvingTableRecipeBuilder define(char c, TagKey<Item> item) {
        return define(c, Ingredient.of(item));
    }

    public CarvingTableRecipeBuilder dye(Ingredient ingredient) {
        dye = ingredient;
        return self();
    }

    public CarvingTableRecipeBuilder dye(ItemLike... items) {
        return dye(Ingredient.of(items));
    }

    public CarvingTableRecipeBuilder dye(Supplier<? extends ItemLike> item) {
        return dye(Ingredient.of(item.get()));
    }

    public CarvingTableRecipeBuilder dye(TagKey<Item> item) {
        return dye(Ingredient.of(item));
    }

    public CarvingTableRecipeBuilder dye(DyeColor color) {
        return dye(Ingredient.of(color.getTag()));
    }

    @Override
    public CarvingTableRecipe build() {
        Ingredient[] is = ingredients.toArray(Ingredient[]::new);
        int[][] ks = new int[][]{
                new int[]{16, 16, 16, 16},
                new int[]{16, 16, 16, 16},
                new int[]{16, 16, 16, 16},
                new int[]{16, 16, 16, 16}};
        for (int i = 0; i < 4; i++) {
            String pattern = patterns[i];
            int len = Math.min(4, pattern.length());
            for (int p = 0; p < len; p++) {
                char k = pattern.charAt(p);
                if (keys.contains(k)) {
                    ks[i][p] = keys.indexOf(k);
                }
            }
        }
        return new CarvingTableRecipe(id, is, ks, dye, output, new CarvingTableRecipe.OriginData(patterns, keys.toCharArray(), is));
    }
}
