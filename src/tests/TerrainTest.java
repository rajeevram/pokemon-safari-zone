package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import javafx.scene.image.Image;
import model.Terrain;

public class TerrainTest {

	// We add this rule to be able to test Alerts within a JavaFX system. Please
	// read the JavaFXThreadingRule class for more information. Without this
	// annotation, we cannot properly JUnit Test our alerting system.
	@Rule
	public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Test
	public void testConstructTerrain() {
		Terrain terrain = new Terrain("terrain.png");
		Terrain.instantiateFlyweights();
		terrain = Terrain.getInstance('G');
		terrain = Terrain.getInstance('+');
		Image image = Terrain.getGround();
		assertTrue(terrain.getImage() != null);
	}

}