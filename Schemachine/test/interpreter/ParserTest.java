package interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

import schema.World;

public class ParserTest {

	private final String OBJECT = "object";
	private final String AFFIRM_PRESENCE = "object exists.";
	private final String DENY_PRESENCE = "object does not exist.";
	
	private final TokenSet emptySet = new TokenSet();
	private final TokenSet declarationSet = new TokenSet(new Keyword[]{Keyword.NAME, Keyword.PERIOD}, new String[]{OBJECT});
	private final TokenSet querySet = new TokenSet(new Keyword[]{Keyword.NAME, Keyword.QUERY}, new String[]{OBJECT});
	
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

}
