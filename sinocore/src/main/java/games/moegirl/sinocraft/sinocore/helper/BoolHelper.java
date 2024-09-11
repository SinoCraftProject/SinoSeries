package games.moegirl.sinocraft.sinocore.helper;

public class BoolHelper {
    public static int compressBool(boolean b1, boolean b2, boolean b3, boolean b4) {
        var result = compressBool(b1, b2, b3);
        result <<= 1;
        result += b4 ? 1 : 0;
        return result;
    }

    public static int compressBool(boolean b1, boolean b2, boolean b3) {
        var result = compressBool(b1, b2);
        result <<= 1;
        result += b3 ? 1 : 0;
        return result;
    }

    public static int compressBool(boolean b1, boolean b2) {
        var result = b1 ? 1 : 0;
        result <<= 1;
        result += b2 ? 1 : 0;
        return result;
    }

    public static int countBool(boolean... b) {
        var result = 0;
        for (boolean value : b) {
            if (value) {
                result += 1;
            }
        }
        return result;
    }
}
