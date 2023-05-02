package games.moegirl.sinocraft.sinocore.tree;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.*;
import java.util.function.Function;

/**
 * @author luqin2007
 */
public class TreeLanguages {

    final Map<String, String> rootNames = new HashMap<>();
    final Table<String, TreeBlockType, List<Function<StringBuilder, StringBuilder>>> names = HashBasedTable.create();
    private final Set<String> langKeys = new HashSet<>();

    public TreeLanguages() {

        put("zh_cn", TreeBlockType.LOG, s -> s.append("原木"));
        put("zh_cn", TreeBlockType.STRIPPED_LOG, s -> s.insert(0, "去皮").append("原木"));
        put("zh_cn", TreeBlockType.LOG_WOOD, Function.identity());
        put("zh_cn", TreeBlockType.STRIPPED_LOG_WOOD, s -> s.insert(0, "去皮"));
        put("zh_cn", TreeBlockType.SAPLING, s -> s.append("树苗"));
        put("zh_cn", TreeBlockType.POTTED_SAPLING, s -> s.append("树苗盆栽"));
        put("zh_cn", TreeBlockType.PLANKS, s -> s.append("木板"));
        put("zh_cn", TreeBlockType.LEAVES, s -> s.append("树叶"));
        put("zh_cn", TreeBlockType.STAIRS, s -> s.append("楼梯"));
        put("zh_cn", TreeBlockType.SLAB, s -> s.append("台阶"));
        put("zh_cn", TreeBlockType.BUTTON, s -> s.append("按钮"));
        put("zh_cn", TreeBlockType.DOOR, s -> s.append("门"));
        put("zh_cn", TreeBlockType.TRAPDOOR, s -> s.append("陷阱门"));
        put("zh_cn", TreeBlockType.FENCE, s -> s.append("栅栏"));
        put("zh_cn", TreeBlockType.FENCE_GATE, s -> s.append("栅栏门"));
        put("zh_cn", TreeBlockType.PRESSURE_PLATE, s -> s.append("压力板"));
        put("zh_cn", TreeBlockType.SIGN, s -> s.append("告示牌"));
        put("zh_cn", TreeBlockType.WALL_SIGN, s -> s.append("告示牌"));
        put("zh_cn", TreeBlockType.HANGING_SIGN, s -> s.insert(0, "悬挂式").append("告示牌"));
        put("zh_cn", TreeBlockType.WALL_HANGING_SIGN, s -> s.insert(0, "悬挂式").append("告示牌"));
        put("zh_cn", TreeBlockType.BOAT, s -> s.append("船"));
        put("zh_cn", TreeBlockType.CHEST_BOAT, s -> s.append("运输船"));

        put("zh_hk", TreeBlockType.LOG, s -> s.append("原木"));
        put("zh_hk", TreeBlockType.STRIPPED_LOG, s -> s.insert(0, "剝皮").append("原木"));
        put("zh_hk", TreeBlockType.LOG_WOOD, Function.identity());
        put("zh_hk", TreeBlockType.STRIPPED_LOG_WOOD, s -> s.insert(0, "剝皮"));
        put("zh_hk", TreeBlockType.SAPLING, s -> s.append("樹苗"));
        put("zh_hk", TreeBlockType.POTTED_SAPLING, s -> s.append("樹苗盆栽"));
        put("zh_hk", TreeBlockType.PLANKS, s -> s.append("板"));
        put("zh_hk", TreeBlockType.LEAVES, s -> s.append("樹葉"));
        put("zh_hk", TreeBlockType.STAIRS, s -> s.append("樓梯"));
        put("zh_hk", TreeBlockType.SLAB, s -> s.append("半磚"));
        put("zh_hk", TreeBlockType.BUTTON, s -> s.append("按鈕"));
        put("zh_hk", TreeBlockType.DOOR, s -> s.append("門"));
        put("zh_hk", TreeBlockType.TRAPDOOR, s -> s.append("地板門"));
        put("zh_hk", TreeBlockType.FENCE, s -> s.append("欄杆"));
        put("zh_hk", TreeBlockType.FENCE_GATE, s -> s.append("閘門"));
        put("zh_hk", TreeBlockType.PRESSURE_PLATE, s -> s.append("壓力板"));
        put("zh_hk", TreeBlockType.SIGN, s -> s.append("指示牌"));
        put("zh_hk", TreeBlockType.WALL_SIGN, s -> s.append("指示牌"));
        put("zh_hk", TreeBlockType.HANGING_SIGN, s -> s.append("吊牌"));
        put("zh_hk", TreeBlockType.WALL_HANGING_SIGN, s -> s.append("吊牌"));
        put("zh_hk", TreeBlockType.BOAT, s -> s.append("船"));
        put("zh_hk", TreeBlockType.CHEST_BOAT, s -> s.insert(0, "儲物箱").append("船"));

        put("zh_tw", TreeBlockType.LOG, s -> s.append("原木"));
        put("zh_tw", TreeBlockType.STRIPPED_LOG, s -> s.insert(0, "剝皮").append("原木"));
        put("zh_tw", TreeBlockType.LOG_WOOD, s -> s.append("塊"));
        put("zh_tw", TreeBlockType.STRIPPED_LOG_WOOD, s -> s.insert(0, "剝皮").append("塊"));
        put("zh_tw", TreeBlockType.SAPLING, s -> s.append("樹苗"));
        put("zh_tw", TreeBlockType.POTTED_SAPLING, s -> s.append("樹苗盆栽"));
        put("zh_tw", TreeBlockType.PLANKS, s -> s.append("材"));
        put("zh_tw", TreeBlockType.LEAVES, s -> s.append("樹葉"));
        put("zh_tw", TreeBlockType.STAIRS, s -> s.append("階梯"));
        put("zh_tw", TreeBlockType.SLAB, s -> s.append("半磚"));
        put("zh_tw", TreeBlockType.BUTTON, s -> s.append("按鈕"));
        put("zh_tw", TreeBlockType.DOOR, s -> s.append("門"));
        put("zh_tw", TreeBlockType.TRAPDOOR, s -> s.append("地板門"));
        put("zh_tw", TreeBlockType.FENCE, s -> s.append("柵欄"));
        put("zh_tw", TreeBlockType.FENCE_GATE, s -> s.append("柵欄門"));
        put("zh_tw", TreeBlockType.PRESSURE_PLATE, s -> s.append("壓力板"));
        put("zh_tw", TreeBlockType.SIGN, s -> s.append("告示牌"));
        put("zh_tw", TreeBlockType.WALL_SIGN, s -> s.append("告示牌"));
        put("zh_tw", TreeBlockType.HANGING_SIGN, s -> s.insert(0, "懸挂式").append("告示牌"));
        put("zh_tw", TreeBlockType.WALL_HANGING_SIGN, s -> s.insert(0, "懸挂式").append("告示牌"));
        put("zh_tw", TreeBlockType.BOAT, s -> s.append("船"));
        put("zh_tw", TreeBlockType.CHEST_BOAT, s -> s.insert(0, "儲物箱").append("船"));

        put("lzh", TreeBlockType.LOG, s -> s.append("樁"));
        put("lzh", TreeBlockType.STRIPPED_LOG, s -> s.insert(0, "既扡").append("樁"));
        put("lzh", TreeBlockType.LOG_WOOD, s -> s.append("木"));
        put("lzh", TreeBlockType.STRIPPED_LOG_WOOD, s -> s.insert(0, "既扡").append("木"));
        put("lzh", TreeBlockType.SAPLING, s -> s.append("秧"));
        put("lzh", TreeBlockType.POTTED_SAPLING, s -> s.append("秧盆景"));
        put("lzh", TreeBlockType.PLANKS, s -> s.append("材"));
        put("lzh", TreeBlockType.LEAVES, s -> s.append("葉"));
        put("lzh", TreeBlockType.STAIRS, s -> s.append("階"));
        put("lzh", TreeBlockType.SLAB, s -> s.append("板"));
        put("lzh", TreeBlockType.BUTTON, s -> s.append("鈕"));
        put("lzh", TreeBlockType.DOOR, s -> s.append("門"));
        put("lzh", TreeBlockType.TRAPDOOR, s -> s.append("窖門"));
        put("lzh", TreeBlockType.FENCE, s -> s.append("檻"));
        put("lzh", TreeBlockType.FENCE_GATE, s -> s.append("扉"));
        put("lzh", TreeBlockType.PRESSURE_PLATE, s -> s.append("踏板"));
        put("lzh", TreeBlockType.SIGN, s -> s.append("牌"));
        put("lzh", TreeBlockType.WALL_SIGN, s -> s.append("牌"));
        put("lzh", TreeBlockType.HANGING_SIGN, s -> s.append("懸牌"));
        put("lzh", TreeBlockType.WALL_HANGING_SIGN, s -> s.append("懸牌"));
        put("lzh", TreeBlockType.BOAT, s -> s.append("舟"));
        put("lzh", TreeBlockType.CHEST_BOAT, s -> s.append("艚"));

        put("en_us", TreeBlockType.LOG, s -> s.append(" Log"));
        put("en_us", TreeBlockType.STRIPPED_LOG, s -> s.insert(0, "Stripped ").append(" Log"));
        put("en_us", TreeBlockType.LOG_WOOD, s -> s.append(" Wood"));
        put("en_us", TreeBlockType.STRIPPED_LOG_WOOD, s -> s.insert(0, "Stripped ").append(" Wood"));
        put("en_us", TreeBlockType.SAPLING, s -> s.append("Sapling"));
        put("en_us", TreeBlockType.POTTED_SAPLING, s -> s.insert(0, "Potted ").append(" Sapling"));
        put("en_us", TreeBlockType.PLANKS, s -> s.append(" Planks"));
        put("en_us", TreeBlockType.LEAVES, s -> s.append(" Leaves"));
        put("en_us", TreeBlockType.STAIRS, s -> s.append(" Stairs"));
        put("en_us", TreeBlockType.SLAB, s -> s.append(" Slab"));
        put("en_us", TreeBlockType.BUTTON, s -> s.append(" Button"));
        put("en_us", TreeBlockType.DOOR, s -> s.append(" Door"));
        put("en_us", TreeBlockType.TRAPDOOR, s -> s.append(" Trapdoor"));
        put("en_us", TreeBlockType.FENCE, s -> s.append(" Fence"));
        put("en_us", TreeBlockType.FENCE_GATE, s -> s.append(" Fence Gate"));
        put("en_us", TreeBlockType.PRESSURE_PLATE, s -> s.append(" Pressure Plate"));
        put("en_us", TreeBlockType.SIGN, s -> s.append(" Sign"));
        put("en_us", TreeBlockType.WALL_SIGN, s -> s.append(" Sign"));
        put("en_us", TreeBlockType.HANGING_SIGN, s -> s.append(" Hanging Sign"));
        put("en_us", TreeBlockType.WALL_HANGING_SIGN, s -> s.append(" Hanging Sign"));
        put("en_us", TreeBlockType.BOAT, s -> s.append(" Boat"));
        put("en_us", TreeBlockType.CHEST_BOAT, s -> s.append(" Boat with Chest"));
    }

    void put(String locale, TreeBlockType type, Function<StringBuilder, StringBuilder> translate) {
        List<Function<StringBuilder, StringBuilder>> list;
        if (names.contains(locale, type)) {
            list = names.get(locale, type);
        } else {
            names.put(locale, type, list = new ArrayList<>());
        }
        assert list != null;
        list.add(translate);
    }

    public void addTranslatesForLocale(String locale, Tree tree, LanguageProvider provider) {
        if (!names.containsRow(locale)) {
            return;
        }

        String root = rootNames.getOrDefault(locale, tree.name.getPath());
        names.row(locale).forEach((type, functions) -> {
            if (type.hasItem() || type.hasBlock()) {
                String name = functions.stream()
                        .reduce(new StringBuilder(root), (s, f) -> f.apply(s), StringBuilder::append)
                        .toString();
                if (type.hasBlock()) {
                    Block block = tree.getBlock(type);
                    String key = block.getDescriptionId();
                    if (!langKeys.contains(key)) {
                        langKeys.add(key);
                        provider.add(key, name);
                    }
                }
                if (type.hasItem()) {
                    Item item = tree.getItem(type);
                    String key = item.getDescriptionId();
                    if (!langKeys.contains(key)) {
                        langKeys.add(key);
                        provider.add(key, name);
                    }
                }
            }
        });
    }
}