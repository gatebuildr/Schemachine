package model;

import static org.junit.Assert.*;
import model.WorldObject;

import org.junit.Test;

public class WorldObjectTest{

	final String NAME_1 = "Object 1";
	final String BURDEN = "Burden";
	final String CONTENT = "Content";
	final String CONTAINER = "Container";
	final String BASE = "Base";
	
	@Test
	public void testInit(){
		WorldObject object = new WorldObject(NAME_1);
		assertEquals(object.getName(), NAME_1);
		assertFalse(object.isContainer());
		assertFalse(object.isSupporter());
		assertFalse(object.contains(object));
	}
		
	@Test
	public void testAddContents(){
		WorldObject object = new WorldObject(NAME_1);
		WorldObject content = new WorldObject(CONTENT);
		object.addContents(content);
		assertTrue(object.isContainer());
		assertTrue(object.contains(content));
	}
	
	@Test
	public void testAddBurden(){
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		object.addBurden(burden);
		assertTrue(object.supports(burden));
		assertTrue(object.isSupporter());
	}

	@Test
	public void testRemoveContents(){
		WorldObject object = new WorldObject(NAME_1);
		WorldObject content = new WorldObject(CONTENT);
		object.addContents(content);
		assertTrue(object.contains(content));
		object.removeContents(content);
		assertFalse(object.contains(content));
		assertTrue(object.isEmpty());
	}

	@Test
	public void testRemoveBurden(){
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		object.addBurden(burden);
		object.removeBurden(burden);
		assertFalse(object.supports(burden));
	}

	@Test(expected = RuntimeException.class)
	public void testContainerLoop(){
		WorldObject object = new WorldObject(NAME_1);
		object.addContents(object);
	}
	
	@Test
	public void testSelfContainment(){
		WorldObject object = new WorldObject(NAME_1);
		WorldObject content = new WorldObject(CONTENT);
		WorldObject container = new WorldObject(CONTAINER);
		object.addContents(content);
		container.addContents(object);
		assertTrue(object.contains(content));
		assertTrue(container.contains(object));
		assertTrue(container.contains(content));
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecursiveContainment(){
		WorldObject content = new WorldObject(CONTENT);
		WorldObject container = new WorldObject(CONTAINER);
		container.addContents(content);
		content.addContents(container);
	}
	
	@Test
	public void testRecursiveSupport(){
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		WorldObject base = new WorldObject(BASE);
		object.addBurden(burden);
		base.addBurden(object);
		assertTrue(object.supports(burden));
		assertTrue(base.supports(object));
		assertTrue(base.supports(burden));
	}
	
	@Test
	public void testContainmentSupportStacking(){
		WorldObject content = new WorldObject(CONTENT);
		WorldObject container = new WorldObject(CONTAINER);
		WorldObject object = new WorldObject(NAME_1);
		WorldObject base = new WorldObject(BASE);
		object.addContents(content);
		base.addBurden(object);
		assertTrue(base.supports(content));
		container.addContents(base);
		assertTrue(container.contains(content));
	}
}