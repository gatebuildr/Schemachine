package interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

import schema.World;
import schema.WorldObject;

public class ParserTest {

	private final String OBJECT = "object";
	private final String CONTAINER = "container";
	private final String SUPPORTER = "supporter";
	private final String AFFIRM_PRESENCE = "object exists.";
	private final String DENY_PRESENCE = "object does not exist.";
	private final String AFFIRM_CONTAINMENT = "Yes, object is in container.";
	
	private final TokenSet emptySet = new TokenSet();
	LexicalAnalyzer analyzer = new LexicalAnalyzer();
	private final TokenSet declarationSet = analyzer.analyze(OBJECT + " .");
	private final TokenSet containerDeclarationSet = analyzer.analyze(OBJECT + " is in " + CONTAINER + ".");
	private final TokenSet containerQuerySet = analyzer.analyze("Is " + OBJECT + " in " + CONTAINER + "?");
	private final TokenSet supporterDeclarationSet = analyzer.analyze(OBJECT + " is on " + SUPPORTER + ".");
	private final TokenSet querySet = analyzer.analyze(OBJECT + "?");
	
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
		parser.parse(containerDeclarationSet);
		assertEquals(parser.parse(containerQuerySet), AFFIRM_CONTAINMENT);
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
