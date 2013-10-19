package parser;

import static org.junit.Assert.*;
import interpreter.TokenSet;


import model.World;
import model.WorldObject;

import org.junit.Test;

import parser.LexicalAnalyzer;
import parser.Parser;


public class ParserTest {

	private final String OBJECT = "object";
	private final String CONTAINER = "container";
	private final String SUPPORTER = "supporter";
	private final String AFFIRM_PRESENCE = "object exists.";
	private final String DENY_PRESENCE = "object does not exist.";
	private final String AFFIRM_CONTAINMENT = "Yes, object is in container.";
	private final String DENY_REVERSE_CONTAINMENT = "No, container is not in object.";
	
	private final TokenSet emptySet = new TokenSet();
	private final TokenSet declarationSet = LexicalAnalyzer.analyze(OBJECT + " .");
	private final TokenSet containerDeclarationSet = LexicalAnalyzer.analyze(OBJECT + " is in " + CONTAINER + ".");
	private final TokenSet containerQuerySet = LexicalAnalyzer.analyze("Is " + OBJECT + " in " + CONTAINER + "?");
	private final TokenSet reverseContainerQuerySet = LexicalAnalyzer.analyze("Is " + CONTAINER + " in " + OBJECT + "?");
	private final TokenSet supporterDeclarationSet = LexicalAnalyzer.analyze(OBJECT + " is on " + SUPPORTER + ".");
	private final TokenSet querySet = LexicalAnalyzer.analyze(OBJECT + "?");
	
	@Test
	public void testIgnoreEmptySet() {
		World world = new World();
		Parser parser = new Parser(world);
		parser.parse(emptySet);
		assertEquals(world, new World());
	}
	
	@Test
	public void testDeclareObject(){
		World world = new World();
		Parser parser = new Parser(world);
		parser.parse(declarationSet);
		assertEquals(world.numObjects(), 1);
	}
	
	@Test
	public void testQueryRealObject(){
		World world = new World();
		Parser parser = new Parser(world);
		parser.parse(declarationSet);
		assertEquals(parser.parse(querySet), AFFIRM_PRESENCE);
	}
	
	@Test
	public void testQueryFalseObject(){
		World world = new World();
		Parser parser = new Parser(world);
		assertEquals(parser.parse(querySet), DENY_PRESENCE);
	}
	
	@Test
	public void testDeclareContainer(){
		World world= new World();
		Parser parser = new Parser(world);
		parser.parse(containerDeclarationSet);
		assertEquals(world.numObjects(), 2);
		WorldObject object = world.findObject(OBJECT);
		WorldObject container = world.findObject(CONTAINER);
		assertNotNull(object);
		assertNotNull(container);
		assertTrue(container.contains(object));
		assertFalse(object.contains(container));
	}
	
	@Test
	public void testQueryContainer(){
		World world = new World();
		Parser parser = new Parser(world);
		assertEquals(parser.parse(containerQuerySet), DENY_PRESENCE);
		parser.parse(containerDeclarationSet);
		assertEquals(parser.parse(containerQuerySet), AFFIRM_CONTAINMENT);
		assertEquals(parser.parse(reverseContainerQuerySet), DENY_REVERSE_CONTAINMENT);
	}
	
	@Test
	public void testDeclareSupporter(){
		World world= new World();
		Parser parser = new Parser(world);
		parser.parse(supporterDeclarationSet);
		assertEquals(world.numObjects(), 2);
		WorldObject object = world.findObject(OBJECT);
		WorldObject supporter = world.findObject(SUPPORTER);
		assertNotNull(object);
		assertNotNull(supporter);
		assertTrue(supporter.supports(object));
		assertFalse(object.supports(supporter));
	}

}
