package games.moegirl.sinocraft.sinobrush;

import games.moegirl.sinocraft.sinobrush.data.gen.tag.SBRItemTags;

public class SBRConstants {
    public static final int COLOR_WHITE = 0xFFFFFF;
    public static final int COLOR_BLACK = 0x000000;

    public static final int DRAWING_COLOR_LENGTH = 4;   // qyl27: Timicasto said the future is not a 4-bit canvas, but a colorful world!
    public static final byte DRAWING_COLOR_MIN = 0;
    public static final byte DRAWING_COLOR_MAX = (byte)(Math.pow(SBRConstants.DRAWING_COLOR_LENGTH, 2) - 1);

    public static final int XUAN_PAPER_MAX_EXPEND = 2;  // 16 << 2 = 64 is large enough, we needn't more.
    public static final int DRAWING_MIN_LENGTH = 16;
    public static final int DRAWING_MAX_LENGTH = DRAWING_MIN_LENGTH << XUAN_PAPER_MAX_EXPEND;

    public static class TagName {
        public static final String DRAWING = "drawing";    // Compound
        public static final String DRAWING_VERSION = "version";    // Int
        public static final String DRAWING_TITLE = "title";    // Component
        public static final String DRAWING_AUTHOR = "author";  // Component
        public static final String DRAWING_DATE = "date";  // Long
        public static final String DRAWING_PIXELS = "pixels";  // ByteArray
        public static final String DRAWING_SIZE = "size";  // Compound {x, y}
        public static final String DRAWING_SIZE_X = "x";   // Int
        public static final String DRAWING_SIZE_Y = "y";   // Int
        public static final String DRAWING_COLOR = "color";    // Compound {paper, ink}
        public static final String DRAWING_COLOR_PAPER = "paper";    // Int(FastColor)
        public static final String DRAWING_COLOR_INK = "ink";    // Int(FastColor)

        public static final String XUAN_PAPER = "xuan_paper";  // Compound
        public static final String XUAN_PAPER_EXPENDS = "expends";  // Int

        public static final String FAN = "fan";  // Compound
        public static final String FAN_LINES = "lines";  // ListTag {String}
    }

    public static class Translation {
        public static final String DRAWING_TITLE_LABEL = "sinobrush.drawing.title.label";
        public static final String DRAWING_TITLE_UNKNOWN = "sinobrush.drawing.title.unknown";
        public static final String DRAWING_AUTHOR_LABEL = "sinobrush.drawing.author.label";
        public static final String DRAWING_AUTHOR_UNKNOWN = "sinobrush.drawing.author.unknown";
        public static final String DRAWING_DATE_LABEL = "sinobrush.drawing.date.label";

        public static final String DESCRIPTION_FAN = "sinobrush.description.fan";
        public static final String DESCRIPTION_FOLDED_FAN = "sinobrush.description.folded_fan";
        public static final String DESCRIPTION_FAN_WROTE = "sinobrush.description.fan.wrote";
        public static final String DESCRIPTION_FOLDED_FAN_WROTE = "sinobrush.description.folded_fan.wrote";

        public static final String DESCRIPTION_ITEM_COLORED = "sinobrush.description.item.colored";
        public static final String DESCRIPTION_XUAN_PAPER_EXPENDED = "sinobrush.description.xuan_paper.expended";
        public static final String DESCRIPTION_FILLED_XUAN_PAPER_SIZE = "sinobrush.description.filled_xuan_paper.size";

        public static final String HUD_FAN_PREFIX = "sinobrush.hud.fan.prefix";

        public static final String GUI_BRUSH_TOOLTIP_SAVE = "sinobrush.gui.brush.tooltip_save";
        public static final String GUI_BRUSH_TOOLTIP_COPY = "sinobrush.gui.brush.tooltip_copy";
        public static final String GUI_BRUSH_TOOLTIP_BRUSH = "sinobrush.gui.brush.tooltip_brush";
        public static final String GUI_BRUSH_TOOLTIP_CLEAR = "sinobrush.gui.brush.tooltip_clear";
        public static final String GUI_BRUSH_HINT_NAME = "sinobrush.gui.brush.hint_name";
        public static final String GUI_BRUSH_CANVAS_COPIED = "sinobrush.gui.brush.canvas_copied";
        public static final String GUI_BRUSH_CANVAS_PASTED = "sinobrush.gui.brush.canvas_pasted";
        public static final String GUI_BRUSH_CANVAS_SAVED = "sinobrush.gui.brush.canvas_saved";
        public static final String GUI_BRUSH_SAVE_SUCCESSFUL = "sinobrush.gui.brush.save_successful";
        public static final String GUI_BRUSH_SAVE_FAILED_NO_INK = "sinobrush.gui.brush.save_failed_no_ink";
        public static final String GUI_BRUSH_SAVE_FAILED_NO_PAPER = "sinobrush.gui.brush.save_failed_no_paper";
        public static final String GUI_BRUSH_SAVE_FAILED_OUTPUT_OCCUPIED = "sinobrush.gui.brush.save_failed_output_occupied";
        public static final String GUI_BRUSH_SAVE_FAILED_NO_BRUSH_ON_HAND = "sinobrush.gui.brush.save_failed_no_brush_on_hand";

        public static final String ADVANCEMENT_ROOT_NAME = "sinobrush.advancement.root.name";
        public static final String ADVANCEMENT_ROOT_DESC = "sinobrush.advancement.root.desc";
        public static final String ADVANCEMENT_BRUSH_NAME = "sinobrush.advancement.brush.name";
        public static final String ADVANCEMENT_BRUSH_DESC = "sinobrush.advancement.brush.desc";
        public static final String ADVANCEMENT_FAN_NAME = "sinobrush.advancement.fan.name";
        public static final String ADVANCEMENT_FAN_DESC = "sinobrush.advancement.fan.description";
        public static final String ADVANCEMENT_UNFOLD_FAN_NAME = "sinobrush.advancement.unfold_fan.name";
        public static final String ADVANCEMENT_UNFOLD_FAN_DESC = "sinobrush.advancement.unfold_fan.description";

        public static final String TAG_FAN = "tag.item.sinobrush.fan";
        public static final String TAG_XUAN_PAPER = "tag.item.sinobrush.xuan_paper";
    }
}
