package games.moegirl.sinocraft.sinocore.utility.block.connection;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * qyl27: Enum for block connection state.
 * 0: none connection.
 * 1: single connection. （材质单缺口）
 * 2: para-connection. （材质对位缺口）
 * 3: ortho-connection. （材质邻位缺口）
 * 4: vicinal-connection. （材质三联缺口）
 * 5: all connection.
 */
public enum ConnectionState implements StringRepresentable {
    NONE(0, "none", false, false, false, false),
    SINGLE(1, "single", false, true, false, false),
    PARA(2, "para", true, false, true, false),
    ORTHO(3, "ortho", false, true, true, false),
    VICINAL(4, "vicinal", false, true, true, true),
    ALL(5, "all", true, true, true, true),
    ;

    private final int id;
    private final String name;
    private final boolean topConnect;
    private final boolean rightConnect;
    private final boolean bottomConnect;
    private final boolean leftConnect;

    ConnectionState(int id, String name, boolean topConnect, boolean rightConnect, boolean bottomConnect, boolean leftConnect) {
        this.id = id;
        this.name = name;
        this.topConnect = topConnect;
        this.rightConnect = rightConnect;
        this.bottomConnect = bottomConnect;
        this.leftConnect = leftConnect;
    }

    public int getId() {
        return id;
    }

    public boolean isTopConnect() {
        return topConnect;
    }

    public boolean isRightConnect() {
        return rightConnect;
    }

    public boolean isBottomConnect() {
        return bottomConnect;
    }

    public boolean isLeftConnect() {
        return leftConnect;
    }

    public String getName() {
        return name;
    }

    public static ConnectionState fromId(int id) {
        return switch (id) {
            case 0 -> NONE;
            case 1 -> SINGLE;
            case 2 -> PARA;
            case 3 -> ORTHO;
            case 4 -> VICINAL;
            case 5 -> ALL;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }

    public static ConnectionState fromSide(boolean top, boolean right, boolean bottom, boolean left) {
        if (top) {
            if (right) {
                if (bottom) {
                    if (left) {
                        return ALL;
                    } else {
                        return VICINAL;
                    }
                } else {
                    if (left) {
                        return VICINAL;
                    } else {
                        return ORTHO;
                    }
                }
            } else {
                if (bottom) {
                    if (left) {
                        return VICINAL;
                    } else {
                        return PARA;
                    }
                } else {
                    if (left) {
                        return ORTHO;
                    } else {
                        return SINGLE;
                    }
                }
            }
        } else {
            if (right) {
                if (bottom) {
                    if (left) {
                        return VICINAL;
                    } else {
                        return ORTHO;
                    }
                } else {
                    if (left) {
                        return PARA;
                    } else {
                        return SINGLE;
                    }
                }
            } else {
                if (bottom) {
                    if (left) {
                        return ORTHO;
                    } else {
                        return SINGLE;
                    }
                } else {
                    if (left) {
                        return SINGLE;
                    } else {
                        return NONE;
                    }
                }
            }
        }
    }

    public static int getRotateBySide(boolean top, boolean right, boolean bottom, boolean left) {
        if (top) {
            if (right) {
                if (bottom) {
                    if (left) {
                        return 0;
                    } else {
                        return 0;
                    }
                } else {
                    if (left) {
                        return 3;
                    } else {
                        return 3;
                    }
                }
            } else {
                if (bottom) {
                    if (left) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else {
                    if (left) {
                        return 2;
                    } else {
                        return 3;
                    }
                }
            }
        } else {
            if (right) {
                if (bottom) {
                    if (left) {
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    if (left) {
                        return 0;
                    } else {
                        return 0;
                    }
                }
            } else {
                if (bottom) {
                    if (left) {
                        return 1;
                    } else {
                        return 1;
                    }
                } else {
                    if (left) {
                        return 2;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    @Override
    public @NotNull String getSerializedName() {
        return getName();
    }
}
