package games.moegirl.sinocraft.sinofeast.taste;

import net.minecraft.client.Minecraft;

import java.util.HashSet;
import java.util.Set;

public class TasteList {
    private static final Set<Taste> tastes = new HashSet<>();

    public static Set<Taste> getTastes() {
        if (tastes.isEmpty()) {
            tastes.addAll(Minecraft.getInstance().getConnection().registryAccess().registryOrThrow(TasteCodec.TASTE_KEY).stream().toList());
        }

        return tastes;
    }
}
