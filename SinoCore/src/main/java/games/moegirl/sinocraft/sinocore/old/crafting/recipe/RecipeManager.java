package games.moegirl.sinocraft.sinocore.old.crafting.recipe;

import games.moegirl.sinocraft.sinocore.crafting.recipe.base.BaseRecipe;
import games.moegirl.sinocraft.sinocore.crafting.recipe.base.BaseSerializer;
import games.moegirl.sinocraft.sinocore.crafting.recipe.base.Recipe;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.registries.IForgeRegistry;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeManager {
    @SubscribeEvent
    public static void onEvent(RegistryEvent.Register<RecipeSerializer<?>> event) {
        IForgeRegistry<RecipeSerializer<?>> registry = event.getRegistry();

        getRecipeClassData().forEach(data -> {
            BaseSerializer<?, ?> baseSerializer = new BaseSerializer<>(data.aClass);
            baseSerializer.setRegistryName(data.value);
            registry.register(baseSerializer);
        });
    }

    public static List<RecipeData<?, ?>> getRecipeClassData() {
        List<ModFileScanData> allScanData = ModList.get().getAllScanData();
        Type type = Type.getType(Recipe.class);

        List<RecipeData<?, ?>> result = new ArrayList<>();

        for (var scanData : allScanData) {
            Set<ModFileScanData.AnnotationData> annotations = scanData.getAnnotations();

            for (var annotation : annotations) {
                if (Objects.equals(annotation.annotationType(), type)) {
                    try {
                        Class<?> aClass = Class.forName(annotation.memberName());

                        if (BaseRecipe.class.isAssignableFrom(aClass)) {
                            result.add(new RecipeData(aClass, (String) annotation.annotationData().get("value")));
                        }
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return result;
                    }
                }
            }
        }

        return result;
    }

    public static record RecipeData<C extends Container, R extends BaseRecipe<C>>(Class<R> aClass, String value) { }
}
