package games.moegirl.sinocraft.sinocore.tree;

import com.google.common.collect.ImmutableMap;
import games.moegirl.sinocraft.sinocore.utility.decorator.StringDecorator;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TreeBlockNameTranslator {
    public static Map<String, Map<TreeBlockType, StringDecorator>> DEFAULT_LOCALED_TRANSLATORS = new HashMap<>();

    public static Map<TreeBlockType, StringDecorator> ZH_CN_TRANSLATORS;
    public static Map<TreeBlockType, StringDecorator> ZH_HK_TRANSLATORS;
    public static Map<TreeBlockType, StringDecorator> ZH_TW_TRANSLATORS;
    public static Map<TreeBlockType, StringDecorator> LZH_TRANSLATORS;
    public static Map<TreeBlockType, StringDecorator> EN_TRANSLATORS;

    static {
        // 简体中文
        {
            ZH_CN_TRANSLATORS = ImmutableMap.<TreeBlockType, StringDecorator>builder()
                    .put(TreeBlockType.LOG, StringDecorator.appendBack("原木"))
                    .put(TreeBlockType.STRIPPED_LOG, StringDecorator.insertMiddle("去皮", "原木"))
                    .put(TreeBlockType.LOG_BARK, StringDecorator.noOp())
                    .put(TreeBlockType.STRIPPED_LOG_BARK, StringDecorator.appendFront("去皮"))
                    .put(TreeBlockType.SAPLING, StringDecorator.appendBack("树苗"))
                    .put(TreeBlockType.PLANKS, StringDecorator.appendBack("木板"))
                    .put(TreeBlockType.LEAVES, StringDecorator.appendBack("树叶"))
                    .put(TreeBlockType.STAIRS, StringDecorator.appendBack("楼梯"))
                    .put(TreeBlockType.SLAB, StringDecorator.appendBack("台阶"))
                    .put(TreeBlockType.BUTTON, StringDecorator.appendBack("按钮"))
                    .put(TreeBlockType.DOOR, StringDecorator.appendBack("门"))
                    .put(TreeBlockType.TRAPDOOR, StringDecorator.appendBack("陷阱门"))
                    .put(TreeBlockType.FENCE, StringDecorator.appendBack("栅栏"))
                    .put(TreeBlockType.FENCE_GATE, StringDecorator.appendBack("栅栏门"))
                    .put(TreeBlockType.PRESSURE_PLATE, StringDecorator.appendBack("压力板"))
                    .put(TreeBlockType.SIGN, StringDecorator.appendBack("告示牌"))
                    .put(TreeBlockType.HANGING_SIGN, StringDecorator.insertMiddle("悬挂式", "告示牌"))
                    .put(TreeBlockType.BOAT, StringDecorator.appendBack("船"))
                    .put(TreeBlockType.CHEST_BOAT, StringDecorator.appendBack("运输船"))
                    .build();
        }

        // 繁體中文（香港）
        {
            ZH_HK_TRANSLATORS = ImmutableMap.<TreeBlockType, StringDecorator>builder()
                    .put(TreeBlockType.LOG, StringDecorator.appendBack("原木"))
                    .put(TreeBlockType.STRIPPED_LOG, StringDecorator.insertMiddle("剝皮", "原木"))
                    .put(TreeBlockType.LOG_BARK, StringDecorator.noOp())
                    .put(TreeBlockType.STRIPPED_LOG_BARK, StringDecorator.appendFront("剝皮"))
                    .put(TreeBlockType.SAPLING, StringDecorator.appendBack("樹苗"))
                    .put(TreeBlockType.PLANKS, StringDecorator.appendBack("板"))
                    .put(TreeBlockType.LEAVES, StringDecorator.appendBack("樹葉"))
                    .put(TreeBlockType.STAIRS, StringDecorator.appendBack("樓梯"))
                    .put(TreeBlockType.SLAB, StringDecorator.appendBack("半磚"))
                    .put(TreeBlockType.BUTTON, StringDecorator.appendBack("按鈕"))
                    .put(TreeBlockType.DOOR, StringDecorator.appendBack("門"))
                    .put(TreeBlockType.TRAPDOOR, StringDecorator.appendBack("地板門"))
                    .put(TreeBlockType.FENCE, StringDecorator.appendBack("欄杆"))
                    .put(TreeBlockType.FENCE_GATE, StringDecorator.appendBack("閘門"))
                    .put(TreeBlockType.PRESSURE_PLATE, StringDecorator.appendBack("壓力板"))
                    .put(TreeBlockType.SIGN, StringDecorator.appendBack("指示牌"))
                    .put(TreeBlockType.HANGING_SIGN, StringDecorator.appendBack("吊牌"))
                    .put(TreeBlockType.BOAT, StringDecorator.appendBack("船"))
                    .put(TreeBlockType.CHEST_BOAT, StringDecorator.insertMiddle("儲物箱", "船"))
                    .build();
        }

        // 正體中文（台灣）
        {
            ZH_TW_TRANSLATORS = ImmutableMap.<TreeBlockType, StringDecorator>builder()
                    .put(TreeBlockType.LOG, StringDecorator.appendBack("原木"))
                    .put(TreeBlockType.STRIPPED_LOG, StringDecorator.insertMiddle("剝皮", "原木"))
                    .put(TreeBlockType.LOG_BARK, StringDecorator.appendBack("塊"))
                    .put(TreeBlockType.STRIPPED_LOG_BARK, StringDecorator.insertMiddle("剝皮", "塊"))
                    .put(TreeBlockType.SAPLING, StringDecorator.appendBack("樹苗"))
                    .put(TreeBlockType.PLANKS, StringDecorator.appendBack("材"))
                    .put(TreeBlockType.LEAVES, StringDecorator.appendBack("樹葉"))
                    .put(TreeBlockType.STAIRS, StringDecorator.appendBack("階梯"))
                    .put(TreeBlockType.SLAB, StringDecorator.appendBack("半磚"))
                    .put(TreeBlockType.BUTTON, StringDecorator.appendBack("按鈕"))
                    .put(TreeBlockType.DOOR, StringDecorator.appendBack("門"))
                    .put(TreeBlockType.TRAPDOOR, StringDecorator.appendBack("地板門"))
                    .put(TreeBlockType.FENCE, StringDecorator.appendBack("柵欄"))
                    .put(TreeBlockType.FENCE_GATE, StringDecorator.appendBack("柵欄門"))
                    .put(TreeBlockType.PRESSURE_PLATE, StringDecorator.appendBack("壓力板"))
                    .put(TreeBlockType.SIGN, StringDecorator.appendBack("告示牌"))
                    .put(TreeBlockType.HANGING_SIGN, StringDecorator.insertMiddle("懸挂式", "告示牌"))
                    .put(TreeBlockType.BOAT, StringDecorator.appendBack("船"))
                    .put(TreeBlockType.CHEST_BOAT, StringDecorator.insertMiddle("儲物箱", "船"))
                    .build();
        }

        // 文言（華夏）
        {
            LZH_TRANSLATORS = ImmutableMap.<TreeBlockType, StringDecorator>builder()
                    .put(TreeBlockType.LOG, StringDecorator.appendBack("樁"))
                    .put(TreeBlockType.STRIPPED_LOG, StringDecorator.insertMiddle("既扡", "樁"))
                    .put(TreeBlockType.LOG_BARK, StringDecorator.appendBack("木"))
                    .put(TreeBlockType.STRIPPED_LOG_BARK, StringDecorator.insertMiddle("既扡", "木"))
                    .put(TreeBlockType.SAPLING, StringDecorator.appendBack("秧"))
                    .put(TreeBlockType.PLANKS, StringDecorator.appendBack("材"))
                    .put(TreeBlockType.LEAVES, StringDecorator.appendBack("葉"))
                    .put(TreeBlockType.STAIRS, StringDecorator.appendBack("階"))
                    .put(TreeBlockType.SLAB, StringDecorator.appendBack("板"))
                    .put(TreeBlockType.BUTTON, StringDecorator.appendBack("鈕"))
                    .put(TreeBlockType.DOOR, StringDecorator.appendBack("門"))
                    .put(TreeBlockType.TRAPDOOR, StringDecorator.appendBack("窖門"))
                    .put(TreeBlockType.FENCE, StringDecorator.appendBack("檻"))
                    .put(TreeBlockType.FENCE_GATE, StringDecorator.appendBack("扉"))
                    .put(TreeBlockType.PRESSURE_PLATE, StringDecorator.appendBack("踏板"))
                    .put(TreeBlockType.SIGN, StringDecorator.appendBack("牌"))
                    .put(TreeBlockType.HANGING_SIGN, StringDecorator.appendBack("懸牌"))
                    .put(TreeBlockType.BOAT, StringDecorator.appendBack("舟"))
                    .put(TreeBlockType.CHEST_BOAT, StringDecorator.appendBack("艚"))
                    .build();
        }

        // English
        {
            EN_TRANSLATORS = ImmutableMap.<TreeBlockType, StringDecorator>builder()
                    .put(TreeBlockType.LOG, StringDecorator.appendBack("Log"))
                    .put(TreeBlockType.STRIPPED_LOG, StringDecorator.insertMiddle("Stripped", "Log"))
                    .put(TreeBlockType.LOG_BARK, StringDecorator.appendBack("Wood"))
                    .put(TreeBlockType.STRIPPED_LOG_BARK, StringDecorator.insertMiddle("Stripped", "Wood"))
                    .put(TreeBlockType.SAPLING, StringDecorator.appendBack("Sapling"))
                    .put(TreeBlockType.PLANKS, StringDecorator.appendBack("Planks"))
                    .put(TreeBlockType.LEAVES, StringDecorator.appendBack("Leaves"))
                    .put(TreeBlockType.STAIRS, StringDecorator.appendBack("Stairs"))
                    .put(TreeBlockType.SLAB, StringDecorator.appendBack("Slab"))
                    .put(TreeBlockType.BUTTON, StringDecorator.appendBack("Button"))
                    .put(TreeBlockType.DOOR, StringDecorator.appendBack("Door"))
                    .put(TreeBlockType.TRAPDOOR, StringDecorator.appendBack("Trapdoor"))
                    .put(TreeBlockType.FENCE, StringDecorator.appendBack("Fence"))
                    .put(TreeBlockType.FENCE_GATE, StringDecorator.appendBack("Fence Gate"))
                    .put(TreeBlockType.PRESSURE_PLATE, StringDecorator.appendBack("Pressure Plate"))
                    .put(TreeBlockType.SIGN, StringDecorator.appendBack("Sign"))
                    .put(TreeBlockType.HANGING_SIGN, StringDecorator.appendBack("Hanging Sign"))
                    .put(TreeBlockType.BOAT, StringDecorator.appendBack("Boat"))
                    .put(TreeBlockType.CHEST_BOAT, StringDecorator.appendBack("Boat with Chest"))
                    .build();
        }

        DEFAULT_LOCALED_TRANSLATORS = ImmutableMap.<String, Map<TreeBlockType, StringDecorator>>builder()
                .put("zh_cn", ZH_CN_TRANSLATORS)
                .put("zh_hk", ZH_HK_TRANSLATORS)
                .put("zh_tw", ZH_HK_TRANSLATORS)
                .put("lzh", LZH_TRANSLATORS)
                .put("en_us", EN_TRANSLATORS)
                .put("en_au", EN_TRANSLATORS)
                .put("en_ca", EN_TRANSLATORS)
                .put("en_gb", EN_TRANSLATORS)
                .put("en_nz", EN_TRANSLATORS)
                .build();
    }

    protected final ResourceLocation name;
    protected final Map<String, String> translateRoots;
    protected final Map<String, Map<TreeBlockType, StringDecorator>> customTranslates;
    protected final Map<String, Map<TreeBlockType, String>> customLiteralTranslates;

    protected final Map<String, Map<String, String>> translateCaches = new HashMap<>();

    public TreeBlockNameTranslator(
            ResourceLocation name,
            Map<String, String> translateRoots,
            Map<String, Map<TreeBlockType, StringDecorator>> customTranslates,
            Map<String, Map<TreeBlockType, String>> customLiteralTranslates
    ) {
        this.name = name;
        this.translateRoots = translateRoots;
        this.customTranslates = customTranslates;
        this.customLiteralTranslates = customLiteralTranslates;
    }

    public Map<String, String> makeTranslatesForLocale(String locale) {
        if (!translateRoots.containsKey(locale)) {
            throw new IllegalArgumentException("There is no root translate for locale: " + locale);
        }

        if (translateCaches.containsKey(locale)) {
            return translateCaches.get(locale);
        }

        var translateRoot = translateRoots.get(locale);
        var map = makeDefaultTranslates(locale, translateRoot);

        if (customTranslates.containsKey(locale)) {
            for (var translateEntry : customTranslates.get(locale).entrySet()) {
                var translateKey = makeTranslateKey(translateEntry.getKey());
                var translateDecorator = translateEntry.getValue();
                map.put(translateKey, translateDecorator.apply(translateRoot));
            }
        }

        if (customLiteralTranslates.containsKey(locale)) {
            for (var literalEntry : customLiteralTranslates.get(locale).entrySet()) {
                var translateKey = makeTranslateKey(literalEntry.getKey());
                var literalTranslate = literalEntry.getValue();
                map.put(translateKey, literalTranslate);
            }
        }

        translateCaches.put(locale, map);
        return map;
    }

    public Map<String, Map<String, String>> makeTranslates() {
        var result = new HashMap<String, Map<String, String>>();

        for (var localedRoots : translateRoots.entrySet()) {
            var locale = localedRoots.getKey();

            if (translateCaches.containsKey(locale)) {
                result.put(locale, translateCaches.get(locale));
            } else {
                result.put(locale, makeTranslatesForLocale(locale));
            }
        }

        return result;
    }

    private Map<String, String> makeDefaultTranslates(String locale, String root) {
        var map = new HashMap<String, String>();

        Map<TreeBlockType, StringDecorator> translators;
        if (DEFAULT_LOCALED_TRANSLATORS.containsKey(locale)) {
            translators = DEFAULT_LOCALED_TRANSLATORS.get(locale);
        } else {
            translators = EN_TRANSLATORS;
        }

        for (var entry : translators.entrySet()) {
            map.put(makeTranslateKey(entry.getKey()), entry.getValue().apply(root));
        }

        return map;
    }

    private String makeTranslateKey(TreeBlockType treeBlockType) {
        return name.getPath() + treeBlockType.getName();
    }
}
