package me.shorturl.Constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    public final Map<Long, Character> longMapper = new HashMap<Long, Character>() {{
        long i = 0;
        for (i = 0; i <= 9; ++i) {
            put(i, (char) (i + 48));
        }
        for (i = 10; i <= 35; ++i) {
            put(i, (char) (i + 87));
        }
        for (i = 36; i <= 61; ++i) {
            put(i, (char) (i + 29));
        }
    }};

    public final Map<Character, Long> charMapper = new HashMap<Character, Long>() {{
        long i = 0;
        for (i = 0; i <= 61; ++i) {
            put(longMapper.get(i), i);
        }
    }};
}
