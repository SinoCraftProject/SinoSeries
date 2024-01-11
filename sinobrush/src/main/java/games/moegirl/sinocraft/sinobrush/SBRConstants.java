package games.moegirl.sinocraft.sinobrush;

import net.minecraft.util.FastColor;

public class SBRConstants {
    public static final int COLOR_WHITE = FastColor.ARGB32.color(255, 255, 255, 255);
    public static final int COLOR_BLACK = FastColor.ARGB32.color(255, 0, 0, 0);

    public static final int DRAWING_MIN_LENGTH = 16;
    public static final int DRAWING_MAX_LENGTH = 256;   // Todo: qyl27: Larger than max packet size?
    public static final int DRAWING_MAX_EXPEND = 4;     // Here is always log_2(DRAWING_MAX_LENGTH / DRAWING_MIN_LENGTH)

    public static final String TAG_NAME_DRAWING = "drawing";    // Compound
    public static final String TAG_NAME_DRAWING_VERSION = "version";    // Int
    public static final String TAG_NAME_DRAWING_TITLE = "title";    // Component
    public static final String TAG_NAME_DRAWING_AUTHOR = "author";  // Component
    public static final String TAG_NAME_DRAWING_DATE = "date";  // Long
    public static final String TAG_NAME_DRAWING_PIXELS = "pixels";  // LongArray
    public static final String TAG_NAME_DRAWING_EXPENDS = "expends";  // Int
    public static final String TAG_NAME_DRAWING_SIZE = "size";  // Compound
    public static final String TAG_NAME_DRAWING_SIZE_X = "x";   // Int
    public static final String TAG_NAME_DRAWING_SIZE_Y = "y";   // Int
    public static final String TAG_TYPE_DRAWING_COLOR = "color";    // Compound
    public static final String TAG_TYPE_DRAWING_COLOR_PAPER = "paper";    // Int(FastColor)
    public static final String TAG_TYPE_DRAWING_COLOR_INK = "ink";    // Int(FastColor)
}
