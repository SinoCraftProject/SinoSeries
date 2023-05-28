package games.moegirl.sinocraft.sinofoundation.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WoodDeskBlock extends HorizontalDirectionalBlock {
    /**
     * qyl27: Property about connect state.
     * 0: no connect.
     * 1: single connect. （材质单缺口）
     * 2: para-connect. （材质对位缺口）
     * 3: ortho-connect. （材质邻位缺口）
     * 4: vicinal-connect. （材质三联缺口）
     * 5: all connect.
     */
    public static final IntegerProperty CONNECT_STATE = IntegerProperty.create("connect", 0, 5);

    public WoodDeskBlock() {
        super(Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED)
                .dynamicShape()
                .strength(2.5f)
                .noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(CONNECT_STATE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var result = updateConnectState(context.getLevel(), context.getClickedPos());
        return super.getStateForPlacement(context)
                .setValue(FACING, result.facing())
                .setValue(CONNECT_STATE, result.state().getId());
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        updateConnectState(level, pos);
    }

    private ConnectUpdateResult updateConnectState(Level level, BlockPos pos) {
        return updateConnectState(level, pos, true, new HashSet<>());
    }

    private ConnectUpdateResult updateConnectState(Level level, BlockPos pos, boolean isStartPos, Set<BlockPos> visited) {
        var northPos = pos.north();
        var north = checkConnectState(level, northPos);
        var eastPos = pos.east();
        var east = checkConnectState(level, eastPos);
        var southPos = pos.south();
        var south = checkConnectState(level, southPos);
        var westPos = pos.west();
        var west = checkConnectState(level, westPos);

        var list = new ArrayList<BlockPos>();
        list.add(northPos);
        list.add(eastPos);
        list.add(southPos);
        list.add(westPos);
        for (var p : list) {
            if (visited.contains(p)) {
                continue;
            }

            if (!checkConnectState(level, p)) {
                continue;
            }

            visited.add(p);
            updateConnectState(level, p, false, visited);
        }

        var state = ConnectState.fromSide(north, east, south, west);
        var rotation = ConnectState.getRotateBySide(north, east, south, west);

        var direction = Direction.NORTH;
        for (var i = 0; i < rotation; i++) {
            direction = direction.getClockWise();
        }

        if (!isStartPos) {
            level.setBlock(pos, level.getBlockState(pos)
                    .setValue(CONNECT_STATE, state.getId())
                    .setValue(FACING, direction), Block.UPDATE_ALL);
        }

        return new ConnectUpdateResult(direction, state);
    }

    private boolean checkConnectState(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        return state.getBlock() instanceof WoodDeskBlock;
    }

    public enum ConnectState {
        NO_CONNECT(0, "wood_desk_no_connection", false, false, false, false),
        SINGLE_CONNECT(1, "wood_desk_single_connection", false, true, false, false),
        PARA_CONNECT(2, "wood_desk_para_connection", true, false, true, false),
        ORTHO_CONNECT(3, "wood_desk_ortho_connection", false, true, true, false),
        VICINAL_CONNECT(4, "wood_desk_vicinal_connection", false, true, true, true),
        ALL_CONNECT(5, "wood_desk_all_connection", true, true, true, true),
        ;

        private final int id;
        private final String name;
        private final boolean topConnect;
        private final boolean rightConnect;
        private final boolean bottomConnect;
        private final boolean leftConnect;

        ConnectState(int id, String name, boolean topConnect, boolean rightConnect, boolean bottomConnect, boolean leftConnect) {
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

        public static ConnectState fromId(int id) {
            return switch (id) {
                case 0 -> NO_CONNECT;
                case 1 -> SINGLE_CONNECT;
                case 2 -> PARA_CONNECT;
                case 3 -> ORTHO_CONNECT;
                case 4 -> VICINAL_CONNECT;
                case 5 -> ALL_CONNECT;
                default -> throw new IllegalStateException("Unexpected value: " + id);
            };
        }

        public static ConnectState fromSide(boolean top, boolean right, boolean bottom, boolean left) {
            if (top) {
                if (right) {
                    if (bottom) {
                        if (left) {
                            return ALL_CONNECT;
                        } else {
                            return VICINAL_CONNECT;
                        }
                    } else {
                        if (left) {
                            return VICINAL_CONNECT;
                        } else {
                            return ORTHO_CONNECT;
                        }
                    }
                } else {
                    if (bottom) {
                        if (left) {
                            return VICINAL_CONNECT;
                        } else {
                            return PARA_CONNECT;
                        }
                    } else {
                        if (left) {
                            return ORTHO_CONNECT;
                        } else {
                            return SINGLE_CONNECT;
                        }
                    }
                }
            } else {
                if (right) {
                    if (bottom) {
                        if (left) {
                            return VICINAL_CONNECT;
                        } else {
                            return ORTHO_CONNECT;
                        }
                    } else {
                        if (left) {
                            return PARA_CONNECT;
                        } else {
                            return SINGLE_CONNECT;
                        }
                    }
                } else {
                    if (bottom) {
                        if (left) {
                            return ORTHO_CONNECT;
                        } else {
                            return SINGLE_CONNECT;
                        }
                    } else {
                        if (left) {
                            return SINGLE_CONNECT;
                        } else {
                            return NO_CONNECT;
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
    }

    public record ConnectUpdateResult(Direction facing, ConnectState state) {
    }
}
