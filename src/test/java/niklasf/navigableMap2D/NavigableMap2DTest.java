package niklasf.navigableMap2D;

import niklasf.navigableMap2D.NavigableMap2D.Range;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class NavigableMap2DTest {

	private static final Function<Position, Range<Position>> RANGE_SUPPLIER = p -> new Range<>(new Position(p.x, p.y - 5), new Position(p.x, p.y + 5));

	private static final Comparator<Range<Position>> RANGE_COMPARATOR = (r1, r2) -> {
		if (r1.upper.y <= r2.lower.y) {
			return -1;
		} else if (r1.lower.y >= r2.upper.y) {
			return 1;
		} else {
			return 0;
		}
	};

	private final NavigableMap2D<Position, Object> set = new NavigableMap2D<>(RANGE_SUPPLIER, RANGE_COMPARATOR);

	private static final Supplier<Position> POSITION_SUPPLIER = () -> new Position(new Random().nextDouble() * 1000, new Random().nextDouble() * 1000);

	@Test
	public void shouldNotContainPositionBeforePut() throws Exception {
		assertFalse(set.containsKey(POSITION_SUPPLIER.get()));
	}

	@Test
	public void shouldContainPositionAfterPut() throws Exception {
		Position p = POSITION_SUPPLIER.get();
		set.put(p, new Object());
		assertTrue(set.containsKey(p));
	}

	@Test
	public void shouldContainFirstPositionAfterInsertingNewInSameRange() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(1, 2);
		set.put(p1, new Object());
		set.put(p2, new Object());
		assertTrue(set.containsKey(p2));
	}

	@Test
	public void shouldNotContainPositionEvenThoughRangeExists() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 2);
		set.put(p1, new Object());
		assertFalse(set.containsKey(p2));
	}

	@Test
	public void shouldNotContainPositionInAnotherRange() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 10);
		set.put(p1, new Object());
		assertFalse(set.containsKey(p2));
	}

	@Test
	public void shouldContainPositionInsertedInAnotherRange() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 10);
		set.put(p1, new Object());
		set.put(p2, new Object());
		assertTrue(set.containsKey(p2));
	}

	@Test
	public void shouldNotContainPositionInOverlappingRange() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 5);
		set.put(p1, new Object());
		assertFalse(set.containsKey(p2));
	}

	@Test
	public void shouldContainPositionInsertedInOverlappingRange() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 5);
		set.put(p1, new Object());
		set.put(p2, new Object());
		assertTrue(set.containsKey(p2));
	}

	@Test
	public void shouldContainFirstPositionInsertedInOverlappingRange() throws Exception {
		Position p1 = new Position(0, 0);
		Position p2 = new Position(0, 5);
		set.put(p1, new Object());
		set.put(p2, new Object());
		assertTrue(set.containsKey(p1));
	}

}
