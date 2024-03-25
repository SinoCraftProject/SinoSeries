package games.moegirl.sinocraft.sinocore.registry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface IMenuRegister extends IRegistry<MenuType<?>> {

    /**
     * 向该注册表注册内容
     *
     * @param name 注册名，实际为 modid:name
     * @return 对象引用
     */
    <T extends AbstractContainerMenu> IRef<MenuType<?>, ?> register(String name, MenuFactory<T> factory);

    interface MenuFactory<T extends AbstractContainerMenu> {

        /**
         * 创建一个新 {@link AbstractContainerMenu}
         *
         * @param id        Menu ID
         * @param inventory {@link Inventory}
         * @param buf       由客户端传入的额外信息 {@link FriendlyByteBuf}
         * @return {@link AbstractContainerMenu} 对象
         */
        T create(int id, Inventory inventory, FriendlyByteBuf buf);
    }
}
