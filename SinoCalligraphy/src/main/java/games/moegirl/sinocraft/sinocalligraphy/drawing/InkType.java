package games.moegirl.sinocraft.sinocalligraphy.drawing;

import games.moegirl.sinocraft.sinocalligraphy.item.SCAItems;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public enum InkType {
    BLACK("black", 1, SCAItems.INK, FastColor.ARGB32.color(255, 0, 0, 0)),
    GOLDEN("golden", 2, SCAItems.GOLDEN_INK, FastColor.ARGB32.color(255, 255, 225, 64)),
    ;

    private final RegistryObject<Item> item;
    private final String typeName;
    private final int id;

    private final int color;

    private InkType(String name, int id, RegistryObject<Item> itemIn, int color) {
        this.typeName = name;
        this.id = id;
        this.item = itemIn;

        this.color = color;
    }

    public static InkType of(int typeId) {
        for (var type : values()) {
            if (type.getId() == typeId) {
                return type;
            }
        }

        return BLACK;
    }

    public String getName() {
        return typeName;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item.get();
    }

    public static InkType of(String name) {
        for (var type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }

        return BLACK;
    }

    public static InkType of(ItemStack stack) {
        for (var type : values()) {
            if (stack.is(type.getItem())) {
                return type;
            }
        }

        return BLACK;
    }

    public int getColor() {
        return color;
    }
}
