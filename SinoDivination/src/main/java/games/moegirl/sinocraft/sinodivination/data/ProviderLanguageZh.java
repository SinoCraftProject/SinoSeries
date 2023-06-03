package games.moegirl.sinocraft.sinodivination.data;

import games.moegirl.sinocraft.sinocore.data.abstracted.AbstractLanguageProvider;
import games.moegirl.sinocraft.sinodivination.SinoDivination;
import games.moegirl.sinocraft.sinodivination.block.SDBlocks;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.data.DataGenerator;

class ProviderLanguageZh extends AbstractLanguageProvider {

    public ProviderLanguageZh(DataGenerator generator) {
        super(generator.getPackOutput(), SinoDivination.MODID, "zh_cn");
    }

    @Override
    public void translate() {
        // group
        addTab(SinoDivination.TAB, "华夏九筮");
        // blocks
        addBlock(SDBlocks.ORE_JADE, "玉矿石");
        addBlock(SDBlocks.ORE_NITER, "硝石矿石");
        addBlock(SDBlocks.ORE_SULPHUR, "硫磺矿石");
        addBlock(SDBlocks.KETTLE_POT, "釜锅");
        addBlock(SDBlocks.WORMWOOD, "艾草");
        addBlock(SDBlocks.GARLIC, "大蒜");
        addBlock(SDBlocks.RICE, "糯稻");
        addBlock(SDBlocks.LUCID_GANODERMA, "灵芝");
        addBlock(SDBlocks.REHMANNIA, "地黄");
        addBlock(SDBlocks.DRAGONLIVER_MELON, "龙肝瓜");
        addBlock(SDBlocks.SESAME, "巨胜");
        addBlock(SDBlocks.ZHU_CAO, "朱草");
        addBlock(SDBlocks.BRIGHT_STEM_GRASS, "明茎草");
        addBlock(SDBlocks.BELLOWS, "风箱");
        addBlock(SDBlocks.SILKWORM_PLAQUE, "蚕匾");
        addBlock(SDBlocks.TRIPOD, "鼎");
        addBlock(SDBlocks.ALTAR, "祭坛");
        addBlock(SDBlocks.CARVING_TABLE, "雕刻台");
        // item
        addItem(SDItems.JADE, "玉石");
        addItem(SDItems.NITER, "硝石");
        addItem(SDItems.SULPHUR, "硫磺");
        addItem(SDItems.STICK_COTINUS, "鬼杀棒");
        addItem(SDItems.CHANGE_SOUP, "变荑汤");
        addItem(SDItems.STICK_JUJUBE, "神力棒");
        addItem(SDItems.LIFE_SYMBOL, "命符");
        addItem(SDItems.JUJUBE, "枣");
        addItem(SDItems.STICK_SOPHORA, "御鬼棒");
        addItem(SDItems.SEED_WORMWOOD, "艾草种子");
        addItem(SDItems.WORMWOOD_LEAF, "艾草叶");
        addItem(SDItems.MOXIBUSTION, "艾灸");
        addItem(SDItems.SEED_GARLIC, "大蒜种子");
        addItem(SDItems.GARLIC, "大蒜");
        addItem(SDItems.RICE, "稻米");
        addItem(SDItems.SEED_RICE, "糯稻种子");
        addItem(SDItems.REHMANNIA, "地黄");
        addItem(SDItems.SEED_REHMANNIA, "地黄种子");
        addItem(SDItems.DRAGONLIVER_MELON, "龙肝瓜");
        addItem(SDItems.SEED_DRAGONLIVER, "龙肝瓜种子");
        addItem(SDItems.SESAME, "芝麻");
        addItem(SDItems.SEED_SESAME, "巨胜种子");
        addItem(SDItems.SILKWORM_BABY, "蚕宝宝");
        addItem(SDItems.HOOK, "钩棍");
        addItem(SDItems.SILK, "丝");
        addItem(SDItems.CANG_BI, "苍壁");
        addItem(SDItems.HUANG_CONG, "黄琮");
        addItem(SDItems.QING_GUI, "青珪");
        addItem(SDItems.CHI_ZHANG, "赤璋");
        addItem(SDItems.BAI_HU, "白琥");
        addItem(SDItems.XUAN_HUANG, "玄璜");
        addItem(SDItems.COPPER_GOBLET, "铜爵");
        addItem(SDItems.COPPER_DAGGER_AXE, "铜戈");
        addItem(SDItems.COPPER_MIRROR, "铜镜");
        addItem(SDItems.COPPER_MASK, "铜面");
        addItem(SDItems.COPPER_LAMP, "铜镫");
        addItem(SDItems.COPPER_BEAST, "铜兽");
        addItem(SDItems.STICK_RICE, "糯米饭");
        // single key
        add(SDLangKeys.CARVING_TABLE_TITLE, "雕刻台");
        add(SDLangKeys.SYMBOL_DATE, "生辰八字：");
        add(SDLangKeys.SYMBOL_NAME, "姓名：");
        add(SDLangKeys.SILKWORM_PLAGUE_TITLE, "蚕匾");
        add(SDLangKeys.JEI_RECIPE_CHANGE_SOUP, "变荑汤");
        add(SDLangKeys.JEI_RECIPE_CARVING_TABLE, "雕刻台");
        add(SDLangKeys.JEI_RECIPE_KETTLE_POT, "釜锅");
        add(SDLangKeys.TOP_BIRTHDAY, "生辰八字(%s): %s");
        add(SDLangKeys.TOP_BIRTHDAY_NO, "生辰八字：无记录");
        add(SDLangKeys.TOP_BLOCK_OWNER, "所有者: %s");
        add(SDLangKeys.TOP_BLOCK_ALLOWED, "允许玩家(%d)");
        // book
        add(SDLangKeys.DIARY0000, "第一天\n");
        add(SDLangKeys.DIARY0001, "我们在山林里迷路了，本以为不会有比这更糟糕的事了，但接下来的确更糟糕——我们遭遇了僵尸！我们的武器根本伤不到那僵尸，其他人都死了，只有我侥幸从山崖上滚下去才得以逃脱。\n");
        add(SDLangKeys.DIARY0002, "但我已经被咬伤了，我能感觉到被病毒感染的血液在我的血管里流动，伤口附近出现红肿，我试图把毒血吸出去，但无济于事，我恐怕也命不久矣······\n");
        add(SDLangKeys.DIARY0100, "第二天\n");
        add(SDLangKeys.DIARY0101, "我的皮肤上开始出现斑块，尽管还没有造成实质性的影响，但我肯定离死亡越来越近了。\n");
        add(SDLangKeys.DIARY0200, "第三天\n");
        add(SDLangKeys.DIARY0201, "我的皮肤溃烂了，我早该自尽的，但我没有勇气那么做······我还不想死！或者，至少在我死之前，请让我用日记写下我们可怕的遭遇，但体内的毒让我心烦意乱，我无从下笔······\n");
        add(SDLangKeys.DIARY0202, "我累了，我想好好睡一觉，尽管不知道我还能否醒来。\n");
        add(SDLangKeys.DIARY0300, "第四天\n");
        add(SDLangKeys.DIARY0301, "我像往常一样醒来，没有不适的感觉，除了身体有些僵硬，也许是太累了吧，我没有在意，但我似乎得救了！\n");
        add(SDLangKeys.DIARY0302, "我遇到了一个人，我已经很多天没有见到人了，我很兴奋，也许他能带我离开这里！\n");
        add(SDLangKeys.DIARY0303, "为什么他一见到我就大声呼救？他向我投掷石头，然后飞快地逃走了，我想去追，然而四肢的僵硬使我寸步难行，为什么？到底发生什么事了？\n");
        add(SDLangKeys.DIARY0304, "我沿着他逃跑的方向缓步前行，终于来到了密林的边缘，感谢他，我得救了！\n");
        add(SDLangKeys.DIARY0305, "然而我还没有迈出去，照进密林的一缕阳光狠狠灼伤了我，若不是因为戴了帽子，恐怕我的脸都要被烧穿！我从没有像现在这样厌恶阳光······为什么我会害怕阳光？从前那是我最喜爱的······为什么？\n");
        add(SDLangKeys.DIARY0306, "我被迫退回林间，我感到口渴，我想喝水，幸运的是不远处就有一眼清泉，我来到泉边，看见自己的倒影，那样貌似乎不像是一个人······我捧起一汪清水，却完全无法下口，我想喝的不是这个，是······血······\n");
        add(SDLangKeys.DIARY0307, "原来我已经不再是人了······\n");
        add(SDLangKeys.DIARY0400, "第五天\n");
        add(SDLangKeys.DIARY0401, "我想活下去，虽然我已经不算是活物了吧，我甚至感受不到自己的心跳了。\n");
        add(SDLangKeys.DIARY0402, "我必须搞清楚现在自己要怎么做，首先阳光会烧伤我，僵硬的身体也使我没法游泳，而且人会害怕我、攻击我······\n");
        add(SDLangKeys.DIARY0403, "趁着现在天还没有亮，也许我该去偷一只鸡来吃······\n");
        add(SDLangKeys.DIARY0404, "很不巧，我盯上的这户人家有一条看门狗，它发现了我，向我吼叫。我不知道自己怎么了······我下意识地咬了它，它哀嚎着逃跑了，但它的血对我来说似乎格外滚烫，灼伤了我的牙龈，然而比起疼痛，我更不能忍受饥饿，我迅速抓了一只鸡，想都没想就生吃了起来，但它的血液似乎更加滚烫，鸡和狗灼热的血液在我的口腔中翻腾，我只好扔下那半只鸡，强忍着疼痛逃走了。\n");
        add(SDLangKeys.DIARY0405, "我已经饿了一天，但鸡和狗的血液会伤害我，我不能吃这些，可我还是好饿······我忍不住了，我想吃新鲜的人肉，喝新鲜的人血······\n");
        add(SDLangKeys.DIARY0500, "第六天\n");
        add(SDLangKeys.DIARY0501, "对不起······强烈的饥饿感已经使我彻底堕落了，我杀了五个人，才得以满足我的食欲。\n");
        add(SDLangKeys.DIARY0502, "吃饱后我感觉身体变轻松了，似乎恢复了生前的活力，也不再感觉鸡和狗的血液滚烫，我有更多用餐选择了，尽管那该死的太阳还是阻碍着我。\n");
        add(SDLangKeys.DIARY0600, "第七天\n");
        add(SDLangKeys.DIARY0601, "我受够了昼伏夜出的感觉，但我对太阳越来越反感，而越来越喜欢月亮，我喜欢沐浴在月光下，抬头看向月亮，贪婪地吸食月亮给予的阴气，阴气能代替鲜血减少我的饥饿感，不过每个晚上只能吸食一次。\n");
        add(SDLangKeys.DIARY0700, "第十六天\n");
        add(SDLangKeys.DIARY0701, "我已经连续九日坚持吸收阴气了，我感到越来越轻松，我有预感今晚将是个阴气极重的夜晚，我更期待今天的晚宴了。\n");
        add(SDLangKeys.DIARY0800, "第十七天\n");
        add(SDLangKeys.DIARY0801, "力量不断涌出，我的身体从未像现在这样轻松，一跃就是两米高，一般的武器对我的攻击不痛不痒，我不再需要呼吸，我甚至不怕阳光和火焰！尽管阳光依旧使我恶心······\n");
        add(SDLangKeys.DIARY0900, "第四十八天\n");
        add(SDLangKeys.DIARY0901, "尽管适应了僵尸的身体，但我已经不能再回到正常的生活中，人们畏惧我，道士们追捕我——尽管最终他们都被打伤逃跑了。\n");
        add(SDLangKeys.DIARY0902, "然而我遇到了一个不同寻常的道士，他见到我后没有立刻攻击，反而是摆起祭阵与我交谈。他提到自己在养尸，听说有一个强大的僵尸在游荡而特意前来寻找，对我来说，他是为数不多待我友善的人了，他答应能帮我找回正常的生活，于是我很快就和他建立了契约。\n");
        add(SDLangKeys.DIARY1000, "第六十天\n");
        add(SDLangKeys.DIARY1001, "道士在傍晚举行了奇怪的仪式，我不知道那是什么。我被要求进入仪式阵内，仪式开始，阵的中心出现了一个发光的头骨，四面突然涌来大量畸形的鬼魂，道士告诉我，那些鬼魂会破坏头骨导致仪式失败，而我则要消灭那些鬼魂保证头骨不被毁掉。\n");
        add(SDLangKeys.DIARY1002, "战斗持续到了午夜，最后一只鬼魂被消灭，头骨暗淡下来。这时道士示意我吃掉那个头骨，我照做了，更强大的力量涌入我的身体，我拥有了金刚不坏之身，并且头一次感受到一股奇异的能量在体内流动。\n");
        add(SDLangKeys.DIARY1100, "第六十八天\n");
        add(SDLangKeys.DIARY1101, "那个该死的道士，他欺骗了我！他在利用我——他偷偷在我身上下了符咒，我完全被他控制，完全按他的指示行动，而他的目的是借助我的力量称霸！我不记得在这一周中我杀了多少人，也不记得多少人被我变成了僵尸，但身体完全不听使唤，我用尽力气想摆脱符咒，却根本扯不下来，我跳入火中想烧毁符咒，它也纹丝不动，那道士就站在我面前嘲讽我。\n");
        add(SDLangKeys.DIARY1102, "“尽管你曾杀过人，但后来即使越来越强大，你也控制住了自己嗜血的欲望，像你这样的僵尸化者并不多，培养你自然是最安全的，这就是为什么我选择了你。黑僵吃掉五个人成为绿僵，绿僵连续十天吸食月亮的阴气成为毛僵，而我在这时找到了你，我的仪式是为了创造出不化骨让你吃掉，这样你就成为了飞僵，之后我会让你杀死千年伏尸，只要你把它吃掉，就能成为游尸了，到时候我们会称霸天下，那时候，人们都会活在我的掌握之中，所有人都要听我的！于是人们便可以不再排挤你，你不就可以回到正常生活中了吗？哈哈，起来！继续为我去杀人！”\n");
        add(SDLangKeys.DIARY1103, "这些话现在想起来只令我恶心，我宁愿活在阴影中苟且偷生。然而突然一阵怪风吹来，符咒被吹成了一堆灰尘，他大惊失色，现在我摆脱控制了，他必须面对我的愤怒了。\n");
        add(SDLangKeys.DIARY1104, "我不记得他是如何求饶的，也不想听，我不会再相信他的鬼话了，活生生掐死了他。\n");
        add(SDLangKeys.DIARY1105, "又一个道士走来，面对凶恶的我他却泰然自若。原来刚才的怪风就是他施的法术。\n");
        add(SDLangKeys.DIARY1106, "“愚蠢的师弟······师父早就告诉过我们出道之人不能心怀不正，你也算是被自己的邪念反噬了。”\n");
        add(SDLangKeys.DIARY1107, "“至于你，你现在打算怎么做？这一带的村落全都被你摧毁了，还有数不清的僵尸在这附近游荡。”\n");
        add(SDLangKeys.DIARY1108, "这的确是我的错，我真不该相信那个小人，无论如何我已经杀了太多人，现在我要怎样赎罪？我已经没有办法了，我告诉他，我会把自己创造的那些僵尸全部消灭，然后我会请求他杀了自己。\n");
        add(SDLangKeys.DIARY1109, "“铲除僵尸我会帮你，杀你那倒不必，念你不是真心助他行邪道，若你还对人间有念想，我能助你恢复人身。”\n");
        add(SDLangKeys.DIARY1200, "第七十二天\n");
        add(SDLangKeys.DIARY1201, "我们把我亲手创造的僵尸都消灭了，而他也兑现了承诺，为我摆放了仪式。\n");
        add(SDLangKeys.DIARY1202, "“可惜，如果你在被僵尸咬伤后的三天之内服用糯米饭或抗尸毒疫苗就可以解除尸毒了，而僵尸化之后则只能依靠这个法术，不过······一旦你恢复人身，作为僵尸的所有修为都会失去，即使这样你也想恢复吗？”\n");
        add(SDLangKeys.DIARY1203, "那是当然的，我根本不在乎什么力量，我走到今天这步不过是偶然，我依然眷恋着人间。\n");
        add(SDLangKeys.DIARY1204, "仪式结束了，我的身体恢复到了七十二天前的样子，但我犯下的罪孽该如何洗净？\n");
        add(SDLangKeys.DIARY1205, "“你不必在意，现在你可以回家了，但如果你想，来我门下做我的弟子也无妨。”\n");
        add(SDLangKeys.DIARY1206, "我选择了后者，从今以后我会产妖除魔，这就是我的救赎。\n");


        // verify
        SDBlocks.REGISTRY.getEntries().forEach(entry -> verifyKey(entry.get().getDescriptionId(), "block"));
        SDItems.REGISTRY.getEntries().forEach(entry -> verifyKey(entry.get().getDescriptionId(), "item"));
        SDLangKeys.TRANSLATION_KEYS.forEach(key -> verifyKey(key, "other"));
    }
}
