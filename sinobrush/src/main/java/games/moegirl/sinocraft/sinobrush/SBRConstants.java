package games.moegirl.sinocraft.sinobrush;

import net.minecraft.util.FastColor;

public class SBRConstants {
    public static final int COLOR_WHITE = FastColor.ARGB32.color(255, 255, 255, 255);
    public static final int COLOR_BLACK = FastColor.ARGB32.color(255, 0, 0, 0);

    public static final int DRAWING_COLOR_LENGTH = 4;   // qyl27: Timicasto said the future is not a 4-bit canvas, but a colorful world!
    public static final int DRAWING_COLOR_COUNT_IN_BYTE = 8 / SBRConstants.DRAWING_COLOR_LENGTH;
    public static final byte DRAWING_COLOR_MASK = 0b1111;
    public static final byte DRAWING_COLOR_MAX = (byte) Math.pow(SBRConstants.DRAWING_COLOR_LENGTH, 2);

    public static final int DRAWING_MIN_LENGTH = 16;
    public static final int DRAWING_MAX_LENGTH = 256;   // Todo: qyl27: Larger than max packet size?
    public static final int XUAN_PAPER_MAX_EXPEND = 4;     // Here is always log_2(DRAWING_MAX_LENGTH / DRAWING_MIN_LENGTH)

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
    }

    public static class Translation {
        public static final String KEY_DRAWING_TITLE_UNKNOWN = "sinobrush.drawing.title.unknown";
        public static final String KEY_DRAWING_AUTHOR_LABEL = "sinobrush.drawing.author.label";
        public static final String KEY_DRAWING_AUTHOR_UNKNOWN = "sinobrush.drawing.author.unknown";
        public static final String KEY_DRAWING_DATE_LABEL = "sinobrush.drawing.date.label";
//        public static final String KEY_DRAWING_DATE_UNKNOWN = "sinobrush.drawing.date.unknown";
    }
}
