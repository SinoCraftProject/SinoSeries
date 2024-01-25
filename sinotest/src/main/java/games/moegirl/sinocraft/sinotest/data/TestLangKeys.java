package games.moegirl.sinocraft.sinotest.data;

import games.moegirl.sinocraft.sinotest.SinoTest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestLangKeys {

    public static final String TEST_WITH_ITEM = lang("test", "item");
    public static final String TEST_WITH_BLOCK = lang("test", "block");

    public static String lang(String... parts) {
        return Stream.concat(Stream.of(SinoTest.MODID), Stream.of(parts)).collect(Collectors.joining("."));
    }
}
