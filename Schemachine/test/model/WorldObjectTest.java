package model;
import static org.junit.Assert.*;

import model.NotAContainerException;
import model.NotASupporterException;
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
	
	@Test(expected = NotAContainerException.class)
	public void testNotContainer() throws NotAContainerException{
		WorldObject object = new WorldObject(NAME_1);
		object.getContents();
	}
	
	@Test(expected = NotASupporterException.class)
	public void testNotSupporter() throws NotASupporterException{
		WorldObject object = new WorldObject(NAME_1);
		object.getEncumbrance();
	}
	
	@Test
	public void testAddContents() throws NotAContainerException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject content = new WorldObject(CONTENT);
		object.addContents(content);
		assertTrue(object.isContainer());
		assertTrue(object.contains(content));
	}
	
	@Test
	public void testRecursiveContainment() throws NotAContainerException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject content = new WorldObject(CONTENT);
		WorldObject container = new WorldObject(CONTAINER);
		object.addContents(content);
		container.addContents(object);
		assertTrue(object.contains(content));
		assertTrue(container.contains(object));
		assertTrue(container.contains(content));
	}
	
	@Test
	public void testRecursiveSupport() throws NotASupporterException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		WorldObject base = new WorldObject(BASE);
		object.addBurden(burden);
		base.addBurden(object);
		assertTrue(object.supports(burden));
		assertTrue(base.supports(object));
		assertTrue(base.supports(burden));
	}
	
	@Test(expected = NotAContainerException.class)
	public void testAddContentsToFrozenObject() throws NotAContainerException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject contents = new WorldObject(CONTENT);
		object.freeze();
		object.addContents(contents);
	}
	
	@Test
	public void testRemoveContents() throws NotAContainerException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject content = new WorldObject(CONTENT);
		object.addContents(content);
		assertTrue(object.contains(content));
		object.removeContents(content);
		assertFalse(object.contains(content));
		assertTrue(object.isEmpty());
	}
	
	@Test
	public void testAddBurden() throws NotASupporterException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		object.addBurden(burden);
		assertTrue(object.supports(burden));
		assertTrue(object.isSupporter());
	}
	
	@Test(expected = NotASupporterException.class)
	public void testAddBurdenToFrozenObject() throws NotASupporterException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		object.freeze();
		object.addBurden(burden);
	}
	
	@Test
	public void testRemoveBurden() throws NotASupporterException{
		WorldObject object = new WorldObject(NAME_1);
		WorldObject burden = new WorldObject(BURDEN);
		object.addBurden(burden);
		object.removeBurden(burden);
		assertFalse(object.supports(burden));
	}
}