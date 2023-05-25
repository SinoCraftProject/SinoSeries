package games.moegirl.sinocraft.sinocore.tree.event;

import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeBlockType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.function.Consumer;

class ProviderRecipe extends RecipeProvider {
    protected final List<Tree> treeTypes;

    public ProviderRecipe(PackOutput output, List<Tree> treeTypes) {
        super(output);

        this.treeTypes = treeTypes;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        for (var tree : treeTypes) {
            planksFromLog(writer, tree.getItem(TreeBlockType.PLANKS), ItemTags.create(tree.getDefaultLogTag()), 4);
            woodFromLogs(writer, tree.getBlock(TreeBlockType.LOG_WOOD), tree.getBlock(TreeBlockType.LOG));
            woodFromLogs(writer, tree.getBlock(TreeBlockType.STRIPPED_LOG_WOOD), tree.getBlock(TreeBlockType.STRIPPED_LOG));
            // woodenBoat(writer, tree.getItem(TreeBlockType.BOAT), tree.getItem(TreeBlockType.PLANKS));
            // chestBoat(writer, tree.getItem(TreeBlockType.CHEST_BOAT), tree.getItem(TreeBlockType.BOAT));

            var family = new BlockFamily.Builder(tree.getBlock(TreeBlockType.PLANKS))
                    .stairs(tree.getBlock(TreeBlockType.STAIRS))
                    .slab(tree.getBlock(TreeBlockType.SLAB))
                    .sign(tree.getBlock(TreeBlockType.SIGN), tree.getBlock(TreeBlockType.WALL_SIGN))
                    .button(tree.getBlock(TreeBlockType.BUTTON))
                    .pressurePlate(tree.getBlock(TreeBlockType.PRESSURE_PLATE))
                    .fence(tree.getBlock(TreeBlockType.FENCE))
                    .fenceGate(tree.getBlock(TreeBlockType.FENCE_GATE))
                    .door(tree.getBlock(TreeBlockType.DOOR))
                    .trapdoor(tree.getBlock(TreeBlockType.TRAPDOOR))
                    .recipeGroupPrefix("wooden")
                    .recipeUnlockedBy("has_planks")
                    .dontGenerateModel()
                    .getFamily();
            generateRecipes(writer, family);

            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, tree.getBlock(TreeBlockType.CHEST))
                    .define('#', tree.getItem(TreeBlockType.PLANKS))
                    .pattern("###")
                    .pattern("# #")
                    .pattern("###")
                    .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0]))
                    .save(writer);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, Blocks.TRAPPED_CHEST)
                    .requires(tree.getBlock(TreeBlockType.CHEST))
                    .requires(Blocks.TRIPWIRE_HOOK)
                    .unlockedBy("has_tripwire_hook", VanillaRecipeProvider.has(Blocks.TRIPWIRE_HOOK))
                    .save(writer);
        }
    }
}
