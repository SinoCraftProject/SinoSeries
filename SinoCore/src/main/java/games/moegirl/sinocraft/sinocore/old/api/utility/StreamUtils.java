package games.moegirl.sinocraft.sinocore.old.api.utility;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Some utils for stream
 */
public class StreamUtils {

    /**
     * Use for {@link Stream#collect(Collector)}, select a random element in the stream

     * @param random random
     * @param <T> element type
     * @return collector to get a random element, or empty if the stream is empty
     */
    public static <T> Collector<T, ?, Optional<T>> random(Random random) {
        return Collector.<T, List<T>, Optional<T>>of(
                ArrayList::new, List::add, StreamUtils::combiner,
                list -> {
                    if (list.isEmpty()) {
                        return Optional.empty();
                    }
                    if (list.size() == 1) {
                        return Optional.of(list.get(0));
                    }
                    return Optional.of(list.get(random.nextInt(list.size())));
                }
        );
    }

    /**
     * Use for {@link Stream#collect(Collector)}, select a random element in the stream

     * @param random random
     * @param <T> element type
     * @return collector to get a random element, or empty if the stream is empty
     */
    public static <T> Collector<T, ?, Stream<T>> randomStream(Random random) {
        return Collector.<T, List<T>, Stream<T>>of(
                ArrayList::new, List::add, StreamUtils::combiner,
                list -> {
                    if (list.isEmpty()) {
                        return Stream.empty();
                    }
                    if (list.size() == 1) {
                        T value = list.get(0);
                        return value == null ? Stream.empty() : Stream.of(value);
                    }
                    return Optional.of(list.get(random.nextInt(list.size()))).stream();
                }
        );
    }

    public static <T, L extends Collection<T>> L combiner(L l1, L l2) {
        l1.addAll(l2);
        return l1;
    }
}
