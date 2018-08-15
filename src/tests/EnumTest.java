package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import model.Direction;
import model.Location;

public class EnumTest {

	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void testEnumClasses() {

		Direction direction1 = Direction.UP;
		assertTrue(direction1.equals(Direction.UP));
		Direction direction2 = Direction.DOWN;
		assertTrue(direction2.equals(Direction.DOWN));
		Direction direction3 = Direction.LEFT;
		assertTrue(direction3.equals(Direction.LEFT));
		Direction direction4 = Direction.RIGHT;
		assertTrue(direction4.equals(Direction.RIGHT));
		Location location1 = Location.BUILDING;
		Location location2 = Location.TOWN;
		Location location3 = Location.SAFARI;
		Location location4 = Location.CAVE;
		String name = location1.getMusicPath();
		assertTrue(name.compareTo(name) == 0);
		name = location2.getMusicPath();
		assertTrue(name.compareTo(name) == 0);
		name = location3.getMusicPath();
		assertTrue(name.compareTo(name) == 0);
		name = location4.getMusicPath();
		assertTrue(name.compareTo(name) == 0);
		assertTrue(location1 == Location.BUILDING);
		assertTrue(location2 == Location.TOWN);
		assertTrue(location3 == Location.SAFARI);
		assertTrue(location4 == Location.CAVE);
	}

}
