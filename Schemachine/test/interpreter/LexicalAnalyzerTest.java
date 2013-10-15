package interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexicalAnalyzerTest {

	private final String EMPTY = "";
	private final String OBJECT = "object";
	private final String DECLARATION = "object.";
	
	private final TokenSet emptySet = new TokenSet();
	private final TokenSet declarationSet = new TokenSet(new Keyword[]{Keyword.NAME, Keyword.PERIOD}, new String[]{OBJECT});
	
	@Test
	public void testEmptyString() {
		assertEquals(LexicalAnalyzer.analyze(EMPTY), emptySet);
	}
	
	@Test
	public void testDeclaration() {
		assertEquals(LexicalAnalyzer.analyze(DECLARATION), declarationSet);
	}

}
