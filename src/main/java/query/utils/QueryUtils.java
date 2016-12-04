package query.utils;

import java.util.Arrays;

public class QueryUtils {
	/**
	 * Casts an array to another
	 * @param original The original array
	 * @param newType The class of the new array type desired
	 * @return The array in the new type
	 */
	public static <T, U> T[] castResult(U[] original, Class<? extends T[]> newType) {
        return Arrays.copyOf(original, original.length, newType);
    }
}
