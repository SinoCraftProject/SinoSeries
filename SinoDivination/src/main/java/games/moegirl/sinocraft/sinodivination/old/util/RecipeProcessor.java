package games.moegirl.sinocraft.sinodivination.old.util;

import games.moegirl.sinocraft.sinocore.crafting.RecipeHolder;
import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * A class for block entity to handle its recipe crafting.
 */
public abstract class RecipeProcessor<E extends BlockEntity, C extends Container, R extends SimpleRecipe<C, R, ?>> {

    protected final RecipeHolder<C, R, ?> holder;
    protected final boolean autoRun;

    @Nullable
    private R recipe = null;
    private int progress = 0;
    private int cooldown = 0;

    @Nullable
    private ResourceLocation resumeRecipe = null;

    private Status status = Status.IDLE;

    public RecipeProcessor(RecipeHolder<C, R, ?> holder, boolean autoRun) {
        this.holder = holder;
        this.autoRun = autoRun;
    }

    public void load(CompoundTag tags) {
        updateTags(tags);
        if (tags.contains("Processor", Tag.TAG_COMPOUND)) {
            CompoundTag processor = tags.getCompound("Processor");
            resumeRecipe = new ResourceLocation(processor.getString("RecipeId"));
            cooldown = processor.getInt("Cooldown");
            progress = processor.getInt("Progress");
            loadProcessor(tags, processor);
            setStatus(Status.RESUME);
        } else {
            setStatus(Status.IDLE);
        }
    }

    protected abstract void loadProcessor(CompoundTag tags, CompoundTag processorTag);

    protected void updateTags(CompoundTag tags) {

    }

    public void save(CompoundTag tags) {
        if (recipe == null) {
            return;
        }

        CompoundTag processor = new CompoundTag();
        processor.putString("RecipeId", recipe.getId().toString());
        processor.putInt("Cooldown", cooldown);
        processor.putInt("Progress", progress);
        saveProcessor(tags, processor);
        tags.put("Processor", processor);
    }

    protected abstract void saveProcessor(CompoundTag tags, CompoundTag processorTag);

    public void tick(E entity) {
        Level level = entity.getLevel();
        switch (status) {
            case RESUME -> {
                if (level != null) {
                    assert resumeRecipe != null;
                    level.getRecipeManager().byKey(resumeRecipe)
                            .filter(r -> r.getType() == holder.recipeType())
                            .ifPresentOrElse(r -> {
                                recipe = (R) r;
                                if (progress >= 100) {
                                    setStatus(Status.BLOCKING);
                                } else if (progress == 0) {
                                    setStatus(Status.READY);
                                } else {
                                    setStatus(Status.RUNNING);
                                }
                                resumeRecipe(recipe);
                            }, () -> setStatus(Status.IDLE));
                }
            }
            case IDLE -> trySetRecipe().ifPresent(r -> {
                recipe = r;
                setStatus(Status.READY);
            });
            case READY -> {
                if (autoRun) {
                    setStatus(Status.RUNNING);
                }
            }
            case RUNNING -> {
                if (cooldown <= 0) {
                    progress += getProcessStep();
                    if (progress >= 100) {
                        assert recipe != null;
                        recipeFinished(recipe);
                        if (hasRemainItems()) {
                            setStatus(Status.BLOCKING);
                        } else {
                            setStatus(Status.IDLE);
                        }
                    }
                    cooldown = getMaxCooldown();
                } else {
                    cooldown--;
                }
            }
            case BLOCKING -> {
                if (!hasRemainItems()) {
                    setStatus(Status.IDLE);
                }
            }
        }
    }

    protected void resumeRecipe(R recipe) {

    }

    protected abstract Optional<R> trySetRecipe();

    protected abstract void recipeFinished(R recipe);

    protected int getMaxCooldown() {
        return 0;
    }

    protected int getProcessStep() {
        return 1;
    }

    protected abstract boolean hasRemainItems();

    private void setStatus(Status status) {
        this.status.exit(this);
        status.checkEnvironment(this);
        status.enter(this);
        this.status = status;
    }

    public void back(int progress) {
        this.progress = Math.max(0, this.progress - progress);
    }

    public void back() {
        back(getProcessStep());
    }

    public void run() {
        if (status == Status.READY) {
            setStatus(Status.RUNNING);
        }
    }

    /**
     * Status for the processor
     */
    public enum Status {
        /**
         * Has no recipe record.
         */
        IDLE {
            @Override
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                processor.recipe = null;
                processor.resumeRecipe = null;
            }
        },

        /**
         * Has a recipe read from nbt and resume environment
         */
        RESUME {
            @Override
            public void checkEnvironment(RecipeProcessor<?, ?, ?> processor) {
                Objects.requireNonNull(processor.resumeRecipe);
            }

            @Override
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                processor.recipe = null;
            }

            @Override
            public void exit(RecipeProcessor<?, ?, ?> processor) {
                processor.resumeRecipe = null;
            }
        },

        /**
         * Has a recipe and ready to run
         */
        READY {
            @Override
            public void checkEnvironment(RecipeProcessor<?, ?, ?> processor) {
                Objects.requireNonNull(processor.recipe);
            }

            @Override
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                processor.progress = 0;
                processor.cooldown = processor.getMaxCooldown();
            }
        },

        /**
         * Has a recipe and is crafting.
         */
        RUNNING {
            @Override
            public void checkEnvironment(RecipeProcessor<?, ?, ?> processor) {
                Objects.requireNonNull(processor.recipe);
            }

            @Override
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                if (processor.progress == 0) {
                    processor.progress = 1;
                }
            }
        },

        /**
         * Has a finished before recipe, but can't start a new recipe.
         */
        BLOCKING {
            @Override
            public void checkEnvironment(RecipeProcessor<?, ?, ?> processor) {
                Objects.requireNonNull(processor.recipe);
            }

            @Override
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                processor.resumeRecipe = null;
                processor.cooldown = 0;
                processor.progress = 100;
            }
        };

        public void checkEnvironment(RecipeProcessor<?, ?, ?> processor) {
        }

        public void enter(RecipeProcessor<?, ?, ?> processor) {
        }

        public void exit(RecipeProcessor<?, ?, ?> processor) {
        }
    }

    public Status getStatus() {
        return status;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getProgress() {
        return progress;
    }

    public RecipeHolder<C, R, ?> getHolder() {
        return holder;
    }

    public Optional<R> getRecipe() {
        return Optional.ofNullable(recipe);
    }

}