package games.moegirl.sinocraft.sinofoundation.data.gen.loot_modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SeedDropModifier extends LootModifier {

    public static final Codec<SeedDropModifier> SEED_DROP_MODIFIER_CODEC = RecordCodecBuilder.create(inst ->
            LootModifier.codecStart(inst)
                    .and(Codec.FLOAT.fieldOf("garlic").forGetter(i -> i.garlic))
                    .apply(inst, SeedDropModifier::new));

    public static SeedDropModifier defaultModifier(float garlic) {
        return new SeedDropModifier(new LootItemCondition[]{
                AnyOfCondition.anyOf(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)
                ).build()
        }, garlic);
    }

    float garlic;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     * @param garlic       大蒜种子掉率
     */
    protected SeedDropModifier(LootItemCondition[] conditionsIn, float garlic) {
        super(conditionsIn);
        this.garlic = garlic;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> objectArrayList, LootContext arg) {
        fill(objectArrayList, arg, garlic, SFDBlockItems.GARLIC);
        return objectArrayList;
    }

    @Override
    public Codec<SeedDropModifier> codec() {
        return SEED_DROP_MODIFIER_CODEC;
    }

    private void fill(ObjectArrayList<ItemStack> objectArrayList, LootContext context, float probability, Supplier<? extends ItemLike> item) {
        if (context.getLevel().random.nextFloat() < probability) {
            objectArrayList.add(new ItemStack(item.get()));
        }
    }
}
