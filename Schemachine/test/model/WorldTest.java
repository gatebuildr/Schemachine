package model;

import static org.junit.Assert.*;


import model.World;
import model.WorldObject;

import org.junit.Test;


public class WorldTest {

	private final String OBJECT_NAME = "object";
	private final WorldObject object = new WorldObject(OBJECT_NAME);
	
	@Test
	public void testInit() {
		World world = new World();
		assertEquals(world.numObjects(), 0);
		assertFalse(world.exists(object));
	}
	
	@Test
	public void testAddObject(){
		World world = new World();
		world.addObject(object);
		assertEquals(world.numObjects(), 1);
		assertTrue(world.exists(object));
	}
	
	@Test
	public void testFindObject(){
		World world = new World();
		world.addObject(object);
		assertEquals(world.findObject(OBJECT_NAME), object);
	}
	
	@Test
	public void testFindMissingObject(){
		World world = new World();
		assertNull(world.findObject(OBJECT_NAME));
	}

}
