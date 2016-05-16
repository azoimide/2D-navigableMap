package niklasf.navigableMap2D;

import java.util.*;
import java.util.function.Function;

public class NavigableMap2D<K extends Comparable<K>, V> {

	private final NavigableMap<Range<K>, NavigableMap<K, Map<K, V>>> rangeMap;
	private final Function<K, Range<K>> rangeSupplier;

	public NavigableMap2D(Function<K, Range<K>> rangeSupplier, Comparator<Range<K>> rangeComparator) {
		this.rangeSupplier = rangeSupplier;
		rangeMap = new TreeMap<>(rangeComparator);
	}

	public boolean put(K k, V v) {
		Range<K> range = new Range<>(k, k);
		NavigableMap<K, Map<K, V>> subRange = rangeMap.get(range);
		if (subRange == null) {
			subRange = new TreeMap<>();
			rangeMap.put(rangeSupplier.apply(k), subRange);
		}
		Map<K, V> matches = subRange.get(k);
		if (matches == null) {
			matches = new HashMap<>();
			subRange.put(k, matches);
		}
		boolean result;
		result = !matches.containsKey(k);
		matches.put(k, v);
		return result;
	}

	public boolean containsKey(K k) {
		Range<K> lookupRange = new Range<>(k, k);
		NavigableMap<K, Map<K, V>> subMap = rangeMap.get(lookupRange);
		if (subMap == null) {
			return false;
		}
		Map<K, V> matches = subMap.get(k);
		return matches != null && matches.containsKey(k);
	}

	public static final class Range<V extends Comparable<V>> {
		public final V lower;
		public final V upper;

		public Range(V lower, V upper) {
			this.lower = lower;
			this.upper = upper;
		}

		@Override
		public String toString() {
			return "Range{" +
				   "lower=" + lower +
				   ", upper=" + upper +
				   '}';
		}
	}
}
