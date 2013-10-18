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
	}

	private void initRules() {
		String[] s1 = {"STATEMENT"};
		String[] s2 = {"QUESTION"};
		RHS[] startRHS = {new RHS(s1), new RHS(s2)};
		rules.put("S", startRHS);
		
		String[] statement1 = {"OBJECT", "."};
		RHS[] statementRHS = {new RHS(statement1)};
		rules.put("STATEMENT", statementRHS);
		
		String[] question1 = {"OBJECT", "?"};
		RHS[] questionRHS = {new RHS(question1)};
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
	}
}
