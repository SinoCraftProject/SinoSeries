package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * Menu 注册表
 */
public interface IMenuRegistry extends IRegistryBase<MenuType<?>> {

    /**
     * 向该注册表注册内容
     *
     * @param name 注册名，实际为 modid:name
     * @return 对象引用
     */
    <T extends AbstractContainerMenu> IRegRef<MenuType<?>> register(String name, MenuFactory<T> factory);

    interface MenuFactory<T extends AbstractContainerMenu> {

        /**
         * 创建一个新 {@link AbstractContainerMenu}
         *
         * @param id        Menu ID
         * @param inventory {@link Inventory}
         * @param buf       由客户端传入的额外信息 {@link RegistryFriendlyByteBuf}
         * @return {@link AbstractContainerMenu} 对象
         */
        T create(int id, Inventory inventory, RegistryFriendlyByteBuf buf);
    }
}
