package games.moegirl.sinocraft.sinobrush.gui.screen;

import games.moegirl.sinocraft.sinobrush.gui.menu.XuanPaperMenu;
import games.moegirl.sinocraft.sinocore.gui.WidgetScreenBase;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.EditBoxWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.ImageButtonWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class XuanPaperScreen extends WidgetScreenBase<XuanPaperMenu> {

    private final ImageButtonWidget[] colorButtons = new ImageButtonWidget[17];
    private ImageButtonWidget clear, brush, download, copy;
    private EditBoxWidget title;

    public XuanPaperScreen(XuanPaperMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    // todo 所有按钮功能及绘画
    @Override
    protected void init() {
        super.init();
        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;
        clear = addButton("button_clear", btn -> player.sendSystemMessage(Component.literal("点击：清除")));
        brush = addButton("button_brush", btn -> player.sendSystemMessage(Component.literal("点击：笔刷")));
        download = addButton("button_download", btn -> player.sendSystemMessage(Component.literal("点击：下载")));
        copy = addButton("button_copy", btn -> player.sendSystemMessage(Component.literal("点击：复制")));
        colorButtons[0] = addButton("button_color_0", btn -> selectColor(0));
        colorButtons[1] = addButton("button_color_1", btn -> selectColor(1));
        colorButtons[2] = addButton("button_color_2", btn -> selectColor(2));
        colorButtons[3] = addButton("button_color_3", btn -> selectColor(3));
        colorButtons[4] = addButton("button_color_4", btn -> selectColor(4));
        colorButtons[5] = addButton("button_color_5", btn -> selectColor(5));
        colorButtons[6] = addButton("button_color_6", btn -> selectColor(6));
        colorButtons[7] = addButton("button_color_7", btn -> selectColor(7));
        colorButtons[8] = addButton("button_color_8", btn -> selectColor(8));
        colorButtons[9] = addButton("button_color_9", btn -> selectColor(9));
        colorButtons[10] = addButton("button_color_10", btn -> selectColor(10));
        colorButtons[11] = addButton("button_color_11", btn -> selectColor(11));
        colorButtons[12] = addButton("button_color_12", btn -> selectColor(12));
        colorButtons[13] = addButton("button_color_13", btn -> selectColor(13));
        colorButtons[14] = addButton("button_color_14", btn -> selectColor(14));
        colorButtons[15] = addButton("button_color_15", btn -> selectColor(15));
        colorButtons[16] = addButton("button_color_16", btn -> selectColor(16));
        title = addEditBox("file_name", s -> player.sendSystemMessage(Component.literal("新名称：" + s)));

        selectColor(0);
    }

    private void selectColor(int color) {
        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;
        player.sendSystemMessage(Component.literal("选择颜色：" + color));
        for (ImageButtonWidget button : colorButtons) {
            button.active = true;
        }
        colorButtons[color].active = false;
    }
}
