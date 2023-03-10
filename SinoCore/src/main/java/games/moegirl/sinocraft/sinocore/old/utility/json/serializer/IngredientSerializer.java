package games.moegirl.sinocraft.sinocore.old.utility.json.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import games.moegirl.sinocraft.sinocore.api.utility.json.BaseSerializer;
import net.minecraft.world.item.crafting.Ingredient;

import java.lang.reflect.Type;

/**
 * @author DustW
 **/
public class IngredientSerializer implements BaseSerializer<Ingredient> {
    @Override
    public Ingredient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Ingredient.fromJson(json);
    }

    @Override
    public JsonElement serialize(Ingredient src, Type typeOfSrc, JsonSerializationContext context) {
        return src.toJson();
    }
}
