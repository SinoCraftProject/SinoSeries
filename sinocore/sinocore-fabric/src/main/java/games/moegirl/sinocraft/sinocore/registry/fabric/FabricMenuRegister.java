package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IMenuRegister;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class FabricMenuRegister extends FabricRegistryImpl<MenuType<?>> implements IMenuRegister {

    FabricMenuRegister(String modId) {
        super(modId, Registries.MENU);
    }

    @Override
    public <T extends AbstractContainerMenu> IRegRef<MenuType<?>, ?> register(String name, MenuFactory<T> factory) {
        return register(name, () -> new ExtendedScreenHandlerType<>(factory::create));
    }
}
