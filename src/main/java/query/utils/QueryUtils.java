package query.utils;

import java.util.Arrays;

public class QueryUtils {
	public static <T, U> T[] castResult(U[] original, Class<? extends T[]> newType) {
        return Arrays.copyOf(original, original.length, newType);
    }
}
