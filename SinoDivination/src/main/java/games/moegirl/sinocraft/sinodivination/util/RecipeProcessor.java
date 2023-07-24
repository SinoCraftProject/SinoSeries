package games.moegirl.sinocraft.sinodivination.util;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.RecipeHolder;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.SimpleRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

/**
 * A class for block entity to handle its recipe crafting.
 */
public abstract class RecipeProcessor<E extends BlockEntity, C extends Container, R extends SimpleRecipe<C, R, ?>> {

    protected final RecipeHolder<C, R, ?> holder;
    protected final boolean autoRun;
    protected boolean isDataChanged = false, isStatusChanged = false;

    @Nullable
    private R recipe = null;  // 当前正在运行的配方
    private int progress = 0; // 处理进度
    private int cooldown = 0; // 两次处理之间的剩余冷却时间

    @Nullable
    private ResourceLocation resumeRecipe = null; // 从 nbt 恢复的配方

    private Status status = Status.IDLE;

    /**
     * @param holder  配方类型
     * @param autoRun 满足条件时是否自动开始
     */
    public RecipeProcessor(RecipeHolder<C, R, ?> holder, boolean autoRun) {
        this.holder = holder;
        this.autoRun = autoRun;
    }

    /**
     * 读取 nbt 信息，应当在 BlockEntity 的对应方法中调用
     *
     * @param nbt nbt
     */
    public void load(CompoundTag nbt) {
        // 更新前的预处理
        updateTags(nbt);
        // 状态和进度等
        if (nbt.contains("Processor", Tag.TAG_COMPOUND)) {
            CompoundTag processor = nbt.getCompound("Processor");
            resumeRecipe = new ResourceLocation(processor.getString("RecipeId"));
            cooldown = processor.getInt("Cooldown");
            progress = processor.getInt("Progress");
            loadProcessor(processor);
            setStatus(Status.RESUME);
        } else {
            setStatus(Status.IDLE);
        }
    }

    /**
     * 读取额外 nbt 信息
     *
     * @param nbt nbt 信息
     */
    protected abstract void loadProcessor(CompoundTag nbt);

    /**
     * 用于 Mod 版本更新或其他情况时预处理已存在的 nbt
     *
     * @param tag nbt
     */
    protected void updateTags(CompoundTag tag) {
    }

    /**
     * 保存 nbt 信息，应当在 BlockEntity 的对应方法中调用
     *
     * @param nbt nbt
     */
    public void save(CompoundTag nbt) {
        if (recipe == null) {
            return;
        }

        CompoundTag processor = new CompoundTag();
        processor.putString("RecipeId", recipe.getId().toString());
        processor.putInt("Cooldown", cooldown);
        processor.putInt("Progress", progress);
        saveProcessor(processor);
        nbt.put("Processor", processor);
    }

    /**
     * 保存额外 nbt 信息
     *
     * @param nbt nbt 信息
     */
    protected abstract void saveProcessor(CompoundTag nbt);

    /**
     * 每 tick 调用
     *
     * @param entity 所在的 BlockEntity
     */
    public void tick(E entity) {
        isStatusChanged = false;
        isDataChanged = false;

        Level level = entity.getLevel();
        switch (status) {
            // 处理从 nbt 中恢复的数据
            case RESUME -> {
                assert resumeRecipe != null;
                Optional.ofNullable(level)
                        .map(Level::getRecipeManager)
                        .map(rm -> rm.byKey(resumeRecipe))
                        .flatMap(Function.identity())
                        .filter(r -> r.getType() == holder.recipeType())
                        .map(r -> (R) r)
                        .ifPresentOrElse(recipe -> {
                            if (progress >= 100) {
                                setStatus(Status.BLOCKING);
                            } else if (progress == 0) {
                                setStatus(Status.READY);
                            } else {
                                setStatus(Status.RUNNING);
                            }
                            onResume(entity, recipe);
                            isDataChanged = true;
                        }, () -> setStatus(Status.IDLE));
            }
            // 空闲状态 - 尝试加载配方
            case IDLE -> {
                onFindRecipe(entity).ifPresent(r -> {
                    recipe = r;
                    setStatus(Status.READY);
                    isDataChanged = true;
                });
            }
            // 配方已经准备好 - 准备运行
            case READY -> {
                if (autoRun) {
                    setStatus(Status.RUNNING);
                }
            }
            // 运行中
            case RUNNING -> {
                if (!isRecipeEffective(entity, recipe)) {
                    setStatus(Status.IDLE);
                } else if (cooldown <= 0) {
                    // 处理 * 1
                    progress += getProcessStep();
                    if (progress >= 100) {
                        assert recipe != null;
                        onFinished(entity, recipe);
                        if (onBlocking(entity)) {
                            setStatus(Status.BLOCKING);
                        } else {
                            setStatus(Status.IDLE);
                        }
                    }
                    cooldown = getMaxCooldown();
                } else {
                    cooldown--; // 冷却
                }
                isDataChanged = true;
            }
            case BLOCKING -> {
                if (!onBlocking(entity)) {
                    setStatus(Status.IDLE);
                }
            }
        }
    }

    /**
     * 当从 nbt 恢复，且的确找到了对应的配方时调用
     *
     * @param entity
     * @param recipe 找到的配方
     */
    protected void onResume(E entity, R recipe) {
    }

    /**
     * 空闲时查找配方
     *
     * @return 找到的配方或空
     */
    protected abstract Optional<R> onFindRecipe(E entity);

    /**
     * 运行时检查正在执行的配方是否有效
     *
     * @return 配方是否有效
     */
    protected abstract boolean isRecipeEffective(E entity, R recipe);

    /**
     * 当配方处理完成时调用
     *
     * @param entity
     * @param recipe 处理完成的配方
     */
    protected abstract void onFinished(E entity, R recipe);

    /**
     * 当已有运行完成，但无法被释放时调用，通常由于产物没有完全排出导致
     *
     * @return 返回 true 表示需要保持 Blocking 状态
     */
    protected abstract boolean onBlocking(E entity);

    /**
     * 最大冷却时间，默认 5 tick
     *
     * @return 最大冷却时间
     */
    protected int getMaxCooldown() {
        return 5;
    }

    /**
     * 每次处理推进的进度，默认 1 tick
     *
     * @return 每次处理推进的进度
     */
    protected int getProcessStep() {
        return 1;
    }

    private void setStatus(Status status) {
        if (status != this.status) {
            this.status.exit(this);
            status.enter(this);
            this.status = status;
            isStatusChanged = true;
        }
    }

    /**
     * 进度回退
     *
     * @param progress 回退的进度
     */
    public void back(int progress) {
        this.progress = Math.max(0, this.progress - progress);
        isDataChanged = true;
    }

    /**
     * 进度回退，退回一次 getProcessStep
     */
    public void back() {
        back(getProcessStep());
    }

    /**
     * 重置处理器
     */
    public void reset() {
        setStatus(Status.IDLE);
        isDataChanged = true;
    }

    /**
     * 当处于 READY 状态时，触发进入 RUNNING 状态
     */
    public void run() {
        if (status == Status.READY) {
            setStatus(Status.RUNNING);
        }
    }

    /**
     * 获取当前状态
     *
     * @return 状态
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 获取处理进度
     *
     * @return 激怒
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 获取处理中的配方
     *
     * @return 配方或 empty
     */
    public Optional<R> getRecipe() {
        return Optional.ofNullable(recipe);
    }

    /**
     * 最近 1 tick 内处理器数据是否发生变化
     * @return 是否变化
     */
    public boolean isDataChanged() {
        return isDataChanged;
    }

    /**
     * 最近 1 tick 内处理器状态是否发生变化
     * @return 是否变化
     */
    public boolean isStatusChanged() {
        return isStatusChanged;
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
                processor.cooldown = 0;
                processor.progress = 0;
                processor.resumeRecipe = null;
            }
        },

        /**
         * Has a recipe read from nbt and resume environment
         */
        RESUME {
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
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                if (processor.progress == 0) {
                    processor.progress = 1;
                }
            }
        },

        /**
         * Before recipe is finished, but can't start a new recipe.
         */
        BLOCKING {
            @Override
            public void enter(RecipeProcessor<?, ?, ?> processor) {
                processor.resumeRecipe = null;
                processor.cooldown = 0;
                processor.progress = 100;
            }
        };

        public void enter(RecipeProcessor<?, ?, ?> processor) {
        }

        public void exit(RecipeProcessor<?, ?, ?> processor) {
        }
    }
}