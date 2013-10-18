package parser;

public class WorldGrammar extends Grammar {
	public WorldGrammar(){
		super();
		initialize();
	}
	
	private void initialize(){
		initRules();
		initPOS();
	}

	private void initPOS() {
		POS.add("OBJECT");
		POS.add(".");
		POS.add("?");
		POS.add("IDENTITY");
		POS.add("PREP");
	}

	private void initRules() {
		String[] s1 = {"DECLARATION"};
		String[] s2 = {"QUESTION"};
		RHS[] startRHS = {new RHS(s1), new RHS(s2)};
		rules.put("S", startRHS);
		
		String[] declaration0 = {"DECLARATION", "."};
		String[] declaration1 = {"OBJECT"};
		String[] declaration2 = {"OBJECT", "IDENTITY", "PREP_PHRASE"};
		RHS[] statementRHS = {new RHS(declaration0), new RHS(declaration1), new RHS(declaration2)};
		rules.put("DECLARATION", statementRHS);
		
		String[] question1 = {"OBJECT", "?"};
		String[] question2 = {"IDENTITY", "OBJECT", "PREP_PHRASE", "?"};
		RHS[] questionRHS = {new RHS(question1), new RHS(question2)};
		rules.put("QUESTION", questionRHS);
		
		String[] n1 = {"NAME"};
		RHS[] nRHS = {new RHS(n1)};
		rules.put("OBJECT", nRHS);
		
		String[] p1 = {"PERIOD"};
		RHS[] pRHS = {new RHS(p1)};
		rules.put(".", pRHS);
		
		String[] q1 = {"QUERY"};
		RHS[] qRHS = {new RHS(q1)};
		rules.put("?", qRHS);
		
		String[] i1 = {"IS"};
		RHS[] identityRHS = {new RHS(i1)};
		rules.put("IDENTITY", identityRHS);
		
		String[] pp1 = {"PREP", "OBJECT"};
		RHS[] prepPhraseRHS = {new RHS(pp1)};
		rules.put("PREP_PHRASE", prepPhraseRHS);
		
		String[] prepIn = {"IN"};
		String[] prepOn = {"ON"};
		RHS[] prepRHS = {new RHS(prepIn), new RHS(prepOn)};
		rules.put("PREP", prepRHS);
	}
}
