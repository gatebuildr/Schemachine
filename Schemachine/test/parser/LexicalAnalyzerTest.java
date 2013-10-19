package parser;

import static org.junit.Assert.*;
import static grammar.Symbols.*;
import interpreter.TokenSet;

import org.junit.Test;

import parser.LexicalAnalyzer;

public class LexicalAnalyzerTest {

	private final String EMPTY = "";
	private final String OBJECT = "object";
	private final String DECLARATION = "object.";
	
	private final TokenSet emptySet = new TokenSet();
	private final TokenSet declarationSet = new TokenSet(new String[]{NAME, PERIOD}, new String[] {OBJECT});
	
	@Test
	public void testEmptyString() {
		assertEquals(LexicalAnalyzer.analyze(EMPTY), emptySet);
	}
	
	@Test
	public void testDeclaration() {
		assertEquals(LexicalAnalyzer.analyze(DECLARATION), declarationSet);
	}
}
